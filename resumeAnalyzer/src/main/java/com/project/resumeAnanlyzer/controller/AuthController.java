package com.project.resumeAnanlyzer.controller;

import com.project.resumeAnanlyzer.dto.AuthRequests.*;
import com.project.resumeAnanlyzer.model.AppUser;
import com.project.resumeAnanlyzer.repository.UserRepository;
import com.project.resumeAnanlyzer.security.JwtService;
import jakarta.validation.Valid;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.web.bind.annotation.*;

@RestController
@RequestMapping("/api/v1/auth")
public class AuthController {

    private final UserRepository users;
    private final PasswordEncoder encoder;
    private final JwtService jwt;

    public AuthController(UserRepository users, PasswordEncoder encoder, JwtService jwt) {
        this.users = users;
        this.encoder = encoder;
        this.jwt = jwt;
    }

    @PostMapping("/register")
    public AuthResponse register(@Valid @RequestBody RegisterRequest req) {
        if (users.existsByEmail(req.email.toLowerCase())) {
            throw new IllegalArgumentException("Email already registered");
        }
        AppUser u = new AppUser();
        u.setEmail(req.email.toLowerCase());
        u.setPasswordHash(encoder.encode(req.password));
        users.save(u);

        String token = jwt.createToken(u.getId(), u.getEmail());
        return new AuthResponse(token, u.getEmail());
    }

    @PostMapping("/login")
    public AuthResponse login(@Valid @RequestBody LoginRequest req) {
        AppUser u = users.findByEmail(req.email.toLowerCase())
                .orElseThrow(() -> new IllegalArgumentException("Invalid credentials"));

        if (!encoder.matches(req.password, u.getPasswordHash())) {
            throw new IllegalArgumentException("Invalid credentials");
        }

        String token = jwt.createToken(u.getId(), u.getEmail());
        return new AuthResponse(token, u.getEmail());
    }
}
