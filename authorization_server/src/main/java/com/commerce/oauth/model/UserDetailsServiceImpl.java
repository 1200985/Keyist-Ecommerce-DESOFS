package com.commerce.oauth.model;

import com.commerce.oauth.dao.UserRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.core.authority.SimpleGrantedAuthority;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.security.core.userdetails.UserDetailsService;
import org.springframework.security.core.userdetails.UsernameNotFoundException;
import org.springframework.stereotype.Service;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import java.util.Collections;
import java.util.List;

@Service(value = "userService")
public class UserDetailsServiceImpl implements UserDetailsService {


    private static final Logger logger = LoggerFactory.getLogger(UserDetailsServiceImpl.class);

    @Autowired
    private UserRepository userRepository;

    @Override
    public UserDetails loadUserByUsername(String email) {
        logger.info("Attempting to load user by email: {}", email);
        User user = userRepository.findByEmail(email).orElseThrow(() -> {
            logger.info("User not found with email: {}", email);
            return new UsernameNotFoundException("Invalid username or password.");
        });

        logger.info("User found ------------------- {}", email);

        return new org.springframework.security.core.userdetails.User(
                user.getEmail(), 
                user.getPassword(), 
                true, 
                true, 
                true, 
                true, 
                getAuthority()
        );
    }

    private List<SimpleGrantedAuthority> getAuthority() {
        return Collections.singletonList(new SimpleGrantedAuthority("ROLE_USER"));
    }

}