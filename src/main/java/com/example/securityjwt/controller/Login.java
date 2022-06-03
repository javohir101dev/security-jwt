package com.example.securityjwt.controller;

import com.example.securityjwt.jwt.JwtProvider;
import com.example.securityjwt.payload.ApiResponse;
import com.example.securityjwt.payload.LoginDto;
import com.example.securityjwt.service.UserLoadService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.authentication.AuthenticationManager;
import org.springframework.security.authentication.UsernamePasswordAuthenticationToken;
import org.springframework.security.core.Authentication;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

@RestController
@RequestMapping("login")
public class Login {

    @Autowired
    JwtProvider jwtProvider;

    @Autowired
    UserLoadService userLoadService;

    @Autowired
    AuthenticationManager authenticationManager;

    @Autowired
    PasswordEncoder passwordEncoder;

    @PostMapping
    public ApiResponse<?> login(@RequestBody LoginDto loginDto){


        UserDetails userDetails = userLoadService.loadUserByUsername(loginDto.getUsername());

        boolean matchesPassword = passwordEncoder.matches(loginDto.getPassword(), userDetails.getPassword());

        if (matchesPassword){

            String token = jwtProvider.generateToken(loginDto.getUsername());

            return new ApiResponse<>(true, "Ok", token);
        }
        return new ApiResponse<>(true, "Login or password wrong", loginDto);
    }

}
