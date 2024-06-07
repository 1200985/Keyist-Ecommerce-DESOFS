package com.commerce.backend.security;

import org.springframework.stereotype.Service;

import java.io.BufferedReader;
import java.io.InputStreamReader;
import java.net.HttpURLConnection;
import java.net.URL;
import java.net.http.HttpClient;
import java.net.http.HttpRequest;
import java.net.http.HttpResponse;
import java.nio.charset.StandardCharsets;
import java.security.MessageDigest;
import java.security.NoSuchAlgorithmException;

@Service
public class PasswordBreachService {

    private static final String HIBP_API_URL = "https://api.pwnedpasswords.com/range/";

    public PasswordBreachService() {
    }

    public static boolean isPasswordBreached(String password) {
        // To protect the value of the source password being searched for, only a partial SHA1 hash is sent to the API
        try {
            String hashedPassword = sha1Hash(password);

            URL url = new URL(HIBP_API_URL + hashedPassword.substring(0, 5));
            HttpURLConnection connection = (HttpURLConnection) url.openConnection();
            connection.setRequestMethod("GET");

            int statusCode = connection.getResponseCode();
            if (statusCode == 200) {
                BufferedReader in = new BufferedReader(new InputStreamReader(connection.getInputStream(), StandardCharsets.UTF_8));
                String inputLine;
                StringBuilder stringBuilder = new StringBuilder();
                while ((inputLine = in.readLine()) != null) {
                    stringBuilder.append(inputLine);
                }
                in.close();

                String response = stringBuilder.toString();
                return response.contains(hashedPassword.substring(5).toUpperCase());
            } else {
                throw new RuntimeException("Error checking if password is breached: " + statusCode);
            }
        } catch (Exception e) {
            throw new RuntimeException("Error checking if password is breached", e);
        }
    }

    // Builds SHA1 hash
    private static String sha1Hash(String plainText) throws NoSuchAlgorithmException {
        MessageDigest messageDigest = MessageDigest.getInstance("SHA-1");
        byte[] result = messageDigest.digest(plainText.getBytes(StandardCharsets.UTF_8));

        StringBuilder stringBuilder = new StringBuilder();
        for (byte b : result) {
            stringBuilder.append(String.format("%02x", b));
        }

        return stringBuilder.toString().toUpperCase();
    }
}
