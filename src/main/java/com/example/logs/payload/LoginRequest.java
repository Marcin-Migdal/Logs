package com.example.logs.payload;

import lombok.Getter;

import javax.validation.constraints.NotBlank;

public class LoginRequest {
    @NotBlank
    @Getter
    private String usernameOrEmail;

    @NotBlank
    @Getter
    private String password;
}