package com.example;

import org.json.JSONObject;
import java.io.*;
import java.net.HttpURLConnection;
import java.net.URI;
import java.net.URL;
import java.nio.charset.StandardCharsets;

public class AuthManager {
    private static final String WEB_API_KEY = "AIzaSyAjPs7COMZBqX1u43K2ziu8S1B-HPutXV8";
    private String idToken;

    public String getIdToken() {
        return idToken;
    }

    public void clearIdToken() {
        idToken = null;
    }

    public void signUp(String email, String password) {
        try {
            String requestUrl = "https://identitytoolkit.googleapis.com/v1/accounts:signUp?key=" + WEB_API_KEY;
            String payload = new JSONObject()
                    .put("email", email)
                    .put("password", password)
                    .put("returnSecureToken", true)
                    .toString();
            String response = sendPostRequest(requestUrl, payload);
            System.out.println("Signup successful: " + response);
        } catch (IOException e) {
            System.err.println("Signup failed: " + e.getMessage());
        }
    }

    public boolean login(String email, String password) {
        try {
            String requestUrl = "https://identitytoolkit.googleapis.com/v1/accounts:signInWithPassword?key=" + WEB_API_KEY;
            String payload = new JSONObject()
                    .put("email", email)
                    .put("password", password)
                    .put("returnSecureToken", true)
                    .toString();
            String response = sendPostRequest(requestUrl, payload);

            JSONObject jsonResponse = new JSONObject(response);
            idToken = jsonResponse.getString("idToken");
            System.out.println("Login successful! ID Token: " + idToken);
            return true;
        } catch (IOException e) {
            System.err.println("Login failed: " + e.getMessage());
            return false;
        }
    }

    private String sendPostRequest(String requestUrl, String payload) throws IOException {
        try {
            URI uri = URI.create(requestUrl);
            URL url = uri.toURL();
            HttpURLConnection conn = (HttpURLConnection) url.openConnection();
            conn.setRequestMethod("POST");
            conn.setRequestProperty("Content-Type", "application/json; charset=UTF-8");
            conn.setDoOutput(true);

            try (OutputStream os = conn.getOutputStream()) {
                byte[] input = payload.getBytes(StandardCharsets.UTF_8);
                os.write(input, 0, input.length);
            }

            StringBuilder response = new StringBuilder();
            try (BufferedReader br = new BufferedReader(new InputStreamReader(conn.getInputStream(), StandardCharsets.UTF_8))) {
                String responseLine;
                while ((responseLine = br.readLine()) != null) {
                    response.append(responseLine.trim());
                }
            }

            conn.disconnect();
            return response.toString();
        } catch (Exception e) {
            throw new IOException("Failed to send POST request: " + e.getMessage(), e);
        }
    }
}