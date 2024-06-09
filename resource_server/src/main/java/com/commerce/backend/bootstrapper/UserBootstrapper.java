package com.commerce.backend.bootstrapper;

import java.util.Optional;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.CommandLineRunner;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.stereotype.Component;

import com.commerce.backend.dao.RoleRepository;
import com.commerce.backend.dao.UserRepository;
import com.commerce.backend.error.exception.InvalidArgumentException;
import com.commerce.backend.model.entity.Role;
import com.commerce.backend.model.entity.RoleEnum;
import com.commerce.backend.model.entity.User;

@Component
public class UserBootstrapper implements CommandLineRunner {

    @Autowired
    private UserRepository userRepository;

    @Autowired
    private PasswordEncoder passwordEncoder;

    @Autowired
    private RoleRepository roleRepository;

    @Override
    public void run(String... args) throws Exception {
        if (userRepository.findByEmail("admin@email.com").isEmpty()) {
            User admin = new User();
            admin.setEmail("admin@email.com");
            admin.setPassword(passwordEncoder.encode("Olaaaa1234??"));
            admin.setEmailVerified(1);

            Optional<Role> role = roleRepository.findByName(RoleEnum.ADMIN);

            if (role.isEmpty()) {
                throw new InvalidArgumentException("Couldn't find the Admin role");
            }

            admin.setRole(role.get());

            userRepository.save(admin);
        }

        if (userRepository.findByEmail("manager@email.com").isEmpty()) {
            User manager = new User();
            manager.setEmail("manager@email.com");
            manager.setPassword(passwordEncoder.encode("Olaaaa1234??"));
            manager.setEmailVerified(1);

            Optional<Role> role = roleRepository.findByName(RoleEnum.MANAGER);

            if (role.isEmpty()) {
                throw new InvalidArgumentException("Couldn't find the Manager role");
            }

            manager.setRole(role.get());

            userRepository.save(manager);
        }

        if (userRepository.findByEmail("user@email.com").isEmpty()) {
            User user = new User();
            user.setEmail("user@email.com");
            user.setPassword(passwordEncoder.encode("Olaaaa1234??"));
            user.setEmailVerified(1);

            Optional<Role> role = roleRepository.findByName(RoleEnum.USER);

            if (role.isEmpty()) {
                throw new InvalidArgumentException("Couldn't find the User role");
            }

            user.setRole(role.get());

            userRepository.save(user);
        }
    }

}