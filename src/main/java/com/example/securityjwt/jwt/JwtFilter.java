package com.example.securityjwt.jwt;

import com.example.securityjwt.service.UserLoadService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.annotation.Lazy;
import org.springframework.security.authentication.UsernamePasswordAuthenticationToken;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.stereotype.Component;
import org.springframework.web.filter.OncePerRequestFilter;

import javax.servlet.FilterChain;
import javax.servlet.ServletException;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import java.io.IOException;
import java.util.ArrayList;

@Component
public class JwtFilter extends OncePerRequestFilter {

    @Autowired
    JwtProvider jwtProvider;

//    @Lazy
    @Autowired
    UserLoadService userLoadService;


    @Override
    protected void doFilterInternal(HttpServletRequest request, HttpServletResponse response, FilterChain filterChain)
            throws ServletException, IOException {

        String token = request.getHeader("Authorization");

        if (token != null && token.startsWith("Bearer ")){

            token = token.substring(7);

            boolean isValidToken = jwtProvider.isValidToken(token);
            if (isValidToken){

                String usernameFromToken = jwtProvider.getUsernameFromToken(token);

                UserDetails userDetails = userLoadService.loadUserByUsername(usernameFromToken);

                SecurityContextHolder.getContext().setAuthentication(
                        new UsernamePasswordAuthenticationToken(
                                userDetails, null,
                                new ArrayList<>()
                        )
                );

            }
        }

        filterChain.doFilter(request, response);
    }
}
