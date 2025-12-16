package com.project.resumeAnanlyzer.dto;

import jakarta.validation.constraints.Email;
import jakarta.validation.constraints.NotBlank;

public class AuthRequests {
    public static class RegisterRequest {
        @Email @NotBlank public String email;
        @NotBlank public String password;
    }

    public static class LoginRequest {
        @Email @NotBlank public String email;
        @NotBlank public String password;
    }

    public static class AuthResponse {
        public String token;
        public String email;
        public AuthResponse(String token, String email) {
            this.token = token; this.email = email;
        }
    }
}
