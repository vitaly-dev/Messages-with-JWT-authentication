package com.github.vitalydev.messages.web;

import java.io.Serializable;

public class AuthenticationResponse implements Serializable {
    private static final long serialVersionUID = 1L;

    private String token;

    public AuthenticationResponse() {
    }

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
