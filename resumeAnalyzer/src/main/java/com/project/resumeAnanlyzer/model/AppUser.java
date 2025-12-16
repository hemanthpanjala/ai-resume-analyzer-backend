package com.project.resumeAnanlyzer.model;

import jakarta.persistence.*;
import java.util.UUID;

@Entity
@Table(name="users")
public class AppUser {

    @Id @GeneratedValue
    private UUID id;

    @Column(nullable=false, unique=true)
    private String email;

    @Column(nullable=false)
    private String passwordHash;

    public AppUser() {}

    public UUID getId() { return id; }
    public String getEmail() { return email; }
    public void setEmail(String email) { this.email = email; }

    public String getPasswordHash() { return passwordHash; }
    public void setPasswordHash(String passwordHash) { this.passwordHash = passwordHash; }
}
