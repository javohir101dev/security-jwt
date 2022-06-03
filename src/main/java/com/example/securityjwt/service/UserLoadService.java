package com.example.securityjwt.service;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.core.userdetails.User;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.security.core.userdetails.UserDetailsService;
import org.springframework.security.core.userdetails.UsernameNotFoundException;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.stereotype.Service;

import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;
import java.util.Optional;

@Service
public class UserLoadService implements UserDetailsService {

    @Autowired
    PasswordEncoder passwordEncoder;

    @Override
    public UserDetails loadUserByUsername(String username) throws UsernameNotFoundException {

        List<User> userList = new ArrayList<>(Arrays.asList(
                new User("user", passwordEncoder.encode("123"), new ArrayList<>()),
                new User("admin", passwordEncoder.encode("123"), new ArrayList<>())
        ));

        Optional<User> optionalUser = userList.stream().filter(u -> u.getUsername().equals(username))
                .findFirst();

        if (optionalUser.isPresent()) {
            return optionalUser.get();
        }
        throw new UsernameNotFoundException(String.format("Username with : %s is not found", username));
    }
}
