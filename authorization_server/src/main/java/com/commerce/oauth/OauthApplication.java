package com.commerce.oauth;

import com.commerce.oauth.config.EnvLoader;
import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;

@SpringBootApplication
public class OauthApplication {

    public static void main(String[] args) {
        EnvLoader.loadEnv();
        SpringApplication.run(OauthApplication.class, args);
    }


}
