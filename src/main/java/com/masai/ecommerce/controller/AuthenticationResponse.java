package com.masai.ecommerce.controller;

public class AuthenticationResponse {

    private final String token;

    public AuthenticationResponse(String token) {
        this.token = token;
    }

    public String getToken() {
        return token;
    }

    // You can add other fields to the response if needed, such as:
    // - user information (e.g., username, roles)
    // - expiration time of the token

    @Override
    public String toString() {
        return "AuthenticationResponse{" +
                "token='" + token + '\'' +
                '}';
    }
}

