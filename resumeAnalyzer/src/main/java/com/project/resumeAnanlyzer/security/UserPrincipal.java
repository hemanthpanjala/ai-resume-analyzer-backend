package com.project.resumeAnanlyzer.security;

import java.util.UUID;

public record UserPrincipal(UUID userId, String email) {}
