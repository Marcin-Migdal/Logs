package com.project.logs.payload;

import lombok.Builder;
import lombok.Getter;

import javax.validation.constraints.NotBlank;

@Builder
public class LoginRequest {
    @NotBlank
    @Getter
    private String usernameOrEmail;

    @NotBlank
    @Getter
    private String password;
}