package com.project.resumeAnanlyzer.security;

import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.http.HttpMethod;
import org.springframework.security.config.Customizer;
import org.springframework.security.config.annotation.web.configuration.EnableWebSecurity;
import org.springframework.security.config.annotation.web.builders.HttpSecurity;
import org.springframework.security.config.http.SessionCreationPolicy;
import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.security.web.SecurityFilterChain;
import org.springframework.security.web.authentication.UsernamePasswordAuthenticationFilter;

@Configuration
@EnableWebSecurity
public class SecurityConfig {

    @Bean
    public SecurityFilterChain filterChain(HttpSecurity http, JwtAuthFilter jwtAuthFilter) throws Exception {

        http
                // REST API -> stateless
                .sessionManagement(sm -> sm.sessionCreationPolicy(SessionCreationPolicy.STATELESS))

                // CORS + disable CSRF for APIs
                .cors(Customizer.withDefaults())
                .csrf(csrf -> csrf.disable())

                // auth rules
                .authorizeHttpRequests(auth -> auth
                        // preflight
                        .requestMatchers(HttpMethod.OPTIONS, "/**").permitAll()

                        // public auth
                        .requestMatchers("/api/v1/auth/**").permitAll()

                        // resume upload extract
                        .requestMatchers(HttpMethod.POST, "/api/resumes/extract-text").permitAll()

                        // job roles (you said: not per user, so keep public)
                        .requestMatchers("/api/v1/job-roles/**").permitAll()

                        // protected routes
                        .requestMatchers("/api/v1/analyses/**").authenticated()
                        .requestMatchers("/api/v1/dashboard/**").authenticated()

                        // everything else
                        .anyRequest().authenticated()
                )

                // JWT filter
                .addFilterBefore(jwtAuthFilter, UsernamePasswordAuthenticationFilter.class);

        return http.build();
    }

    @Bean
    public PasswordEncoder passwordEncoder() {
        return new BCryptPasswordEncoder();
    }
}
