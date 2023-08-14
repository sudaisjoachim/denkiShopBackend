package com.denkishop.security;

import java.util.Optional;

import com.denkishop.user.User;

import lombok.Data;

@Data
public class JwtResponse {

    private String accessToken;
    private Optional<User> user;

    public JwtResponse(String accessToken, Optional<User> foundusr) {
		this.accessToken = accessToken;
		this.user = foundusr;
	}

	public JwtResponse(String accessToken, String tokenType, Optional<User> user) {
		super();
		this.accessToken = accessToken;
		this.user = user;
	}

	public JwtResponse(String accessToken) {
        this.accessToken = accessToken;
    }

	public String getAccessToken() {
		return accessToken;
	}

	public Optional<User> getUser() {
		return user;
	}

}
