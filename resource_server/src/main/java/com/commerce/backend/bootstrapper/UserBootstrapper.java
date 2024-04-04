package com.commerce.backend.bootstrapper;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.CommandLineRunner;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.stereotype.Component;

import com.commerce.backend.dao.UserRepository;
import com.commerce.backend.model.entity.Role;
import com.commerce.backend.model.entity.User;

@Component
public class UserBootstrapper implements CommandLineRunner {

    @Autowired
    private UserRepository userRepository;

    @Autowired
    private PasswordEncoder passwordEncoder;

    @Override
    public void run(String... args) throws Exception {
        if (userRepository.findByEmail("admin@email.com").isEmpty()) {
            User admin = new User();
            admin.setEmail("admin@email.com");
            admin.setPassword(passwordEncoder.encode("123123"));
            admin.setEmailVerified(1);

            admin.setRole(Role.ADMIN);

            userRepository.save(admin);
        }

        if (userRepository.findByEmail("manager@email.com").isEmpty()) {
            User manager = new User();
            manager.setEmail("manager@email.com");
            manager.setPassword(passwordEncoder.encode("123123"));
            manager.setEmailVerified(1);

            manager.setRole(Role.MANAGER);

            userRepository.save(manager);
        }

        if (userRepository.findByEmail("client@email.com").isEmpty()) {
            User client = new User();
            client.setEmail("client@email.com");
            client.setPassword(passwordEncoder.encode("123123"));
            client.setEmailVerified(1);

            client.setRole(Role.CLIENT);

            userRepository.save(client);
        }
    }

}
