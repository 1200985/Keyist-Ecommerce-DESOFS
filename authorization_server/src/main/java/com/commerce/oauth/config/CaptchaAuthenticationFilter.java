package com.commerce.oauth.config;

import com.fasterxml.jackson.databind.ObjectMapper;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.context.annotation.Configuration;
import org.springframework.security.access.AccessDeniedException;
import org.springframework.security.authentication.AuthenticationManager;
import org.springframework.security.authentication.AuthenticationServiceException;
import org.springframework.security.core.Authentication;
import org.springframework.security.web.authentication.UsernamePasswordAuthenticationFilter;
import org.springframework.web.client.RestTemplate;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import java.io.IOException;
import java.util.Map;

@Configuration
public class CaptchaAuthenticationFilter extends UsernamePasswordAuthenticationFilter {

    private final ObjectMapper objectMapper = new ObjectMapper();
    private final RestTemplate restTemplate = new RestTemplate();

    @Value("${spring.recaptcha.secret}")
    private String recaptchaSecret;

    public CaptchaAuthenticationFilter(AuthenticationManager authenticationManager) {
        super.setAuthenticationManager(authenticationManager);
    }

    @Override
    public Authentication attemptAuthentication(HttpServletRequest request, HttpServletResponse response) throws AuthenticationServiceException {
        logger.info("Attempting to verify captcha response");

        if (!request.getMethod().equals("POST")) {
            throw new AuthenticationServiceException("Authentication request method not supported.");
        }

        String captchaResponse = obtainCaptchaResponse(request);
        if (!isValidCaptcha(captchaResponse)) {
            throw new AccessDeniedException("Invalid CAPTCHA response.");
        }
        logger.info("Captcha successfully validated");
        return super.attemptAuthentication(request, response);
    }

    private String obtainCaptchaResponse(HttpServletRequest request) {
        try {
            Map<String, String> map = objectMapper.readValue(request.getInputStream(), Map.class);
            return map.get("recaptchaResponse");
        } catch (IOException e) {
            return "";
        }
    }

    private boolean isValidCaptcha(String captchaResponse) {
        String url = "https://www.google.com/recaptcha/api/siteverify?secret={secret}&response={response}";
        String googleResponse = restTemplate.postForObject(url, null, String.class, recaptchaSecret, captchaResponse);

        try {
            Map<String, Object> response = objectMapper.readValue(googleResponse, Map.class);
            return (boolean) response.get("success");
        } catch (IOException e) {
            return false;
        }
    }
}
