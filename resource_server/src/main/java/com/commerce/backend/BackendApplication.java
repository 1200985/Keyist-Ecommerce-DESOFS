package com.commerce.backend;

import com.commerce.backend.config.EnvLoader;
import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;

@SpringBootApplication
public class BackendApplication {

    public static void main(String[] args) {
        EnvLoader.loadEnv();
        SpringApplication.run(BackendApplication.class, args);
    }

}
