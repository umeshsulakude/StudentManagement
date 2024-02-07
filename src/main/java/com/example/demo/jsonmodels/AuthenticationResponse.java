package com.example.demo.jsonmodels;

public class AuthenticationResponse {
    private String token;

    public AuthenticationResponse(){}
    public AuthenticationResponse(String token) {
        this.token = token;
    }

    public String getToken() {
        return token;
    }

    public void setToken(String token) {
        this.token = token;
    }
}
