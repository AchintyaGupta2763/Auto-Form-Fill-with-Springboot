// backend/src/main/java/com/srm/trackportal/config/SecurityConfig.java
package com.srm.trackportal.config;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.http.HttpMethod;
import org.springframework.security.authentication.AuthenticationManager;
import org.springframework.security.authentication.dao.DaoAuthenticationProvider;
import org.springframework.security.config.annotation.authentication.configuration.AuthenticationConfiguration;
import org.springframework.security.config.annotation.web.configuration.EnableWebSecurity; 
import org.springframework.security.config.annotation.web.builders.HttpSecurity;
import org.springframework.security.config.http.SessionCreationPolicy;
import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.security.web.SecurityFilterChain;

import org.springframework.web.cors.CorsConfiguration;
import org.springframework.web.cors.CorsConfigurationSource;
import org.springframework.web.cors.UrlBasedCorsConfigurationSource;

import com.srm.trackportal.service.CustomUserDetailsService;

import java.util.List;

@Configuration
@EnableWebSecurity
public class SecurityConfig {

    @Autowired
    private CustomUserDetailsService customUserDetailsService;

    @Bean
    public PasswordEncoder passwordEncoder() {
        return new BCryptPasswordEncoder();
    }

    @Bean
    public DaoAuthenticationProvider authenticationProvider() {
        DaoAuthenticationProvider authProvider = new DaoAuthenticationProvider();
        authProvider.setUserDetailsService(customUserDetailsService);
        authProvider.setPasswordEncoder(passwordEncoder());
        return authProvider;
    }

    @Bean
    public SecurityFilterChain filterChain(HttpSecurity http) throws Exception {
        CorsConfigurationSource corsSource = corsConfigurationSource();

        http
            // 1) Enable CORS using our corsConfigurationSource() bean
            .cors().configurationSource(corsSource).and()

            // 2) Stateless: no HTTP session
            .sessionManagement()
                .sessionCreationPolicy(SessionCreationPolicy.STATELESS)
                .and()

            // 3) Disable CSRF (we're not using cookies)
            .csrf().disable()

            // 4) Turn off default form‐login and HTTP Basic
            .httpBasic().disable()
            .formLogin().disable()

            // 5) Set our custom authentication provider
            .authenticationProvider(authenticationProvider())

            // 6) Authorization rules
            .authorizeHttpRequests()
                // 6a) Allow all preflight (OPTIONS) requests
                .requestMatchers(HttpMethod.OPTIONS, "/**").permitAll()
                // 6b) Allow everything under /api/auth/** (signup, login) without auth
                .requestMatchers("/api/auth/**").permitAll()
                // 6c) All other endpoints require a valid JWT
                .anyRequest().authenticated();

        return http.build();
    }

    @Bean
    public CorsConfigurationSource corsConfigurationSource() {
        CorsConfiguration config = new CorsConfiguration();

        // Allow React dev server origin
        config.setAllowedOrigins(List.of("http://localhost:3000"));
        // Allow any header (including Content-Type)
        config.setAllowedHeaders(List.of("*"));
        // Allow methods including OPTIONS for preflight
        config.setAllowedMethods(List.of("GET", "POST", "PUT", "DELETE", "OPTIONS"));
        // If you ever send credentials/cookies, allow them
        config.setAllowCredentials(true);

        UrlBasedCorsConfigurationSource source = new UrlBasedCorsConfigurationSource();
        // Apply the above CORS config to all paths
        source.registerCorsConfiguration("/**", config);
        return source;
    }

    @Bean
    public AuthenticationManager authenticationManager(
            AuthenticationConfiguration authConfig) throws Exception {
        return authConfig.getAuthenticationManager();
    }
}