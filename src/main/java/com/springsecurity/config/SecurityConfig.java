package com.springsecurity.config;

import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.http.HttpMethod;
import org.springframework.security.authentication.AuthenticationProvider;
import org.springframework.security.config.annotation.web.builders.HttpSecurity;
import org.springframework.security.config.annotation.web.configuration.EnableWebSecurity;
import org.springframework.security.config.http.SessionCreationPolicy;
import org.springframework.security.web.SecurityFilterChain;
import org.springframework.security.web.authentication.UsernamePasswordAuthenticationFilter;

import com.springsecurity.entities.Permission;
import com.springsecurity.filters.JwtFilter;

import lombok.RequiredArgsConstructor;

@Configuration
@EnableWebSecurity
@RequiredArgsConstructor
public class SecurityConfig {

  private final AuthenticationProvider authenticationProvider;
  private final JwtFilter jwtFilter;

  @Bean
  SecurityFilterChain securityFilterChain(HttpSecurity httpSecurity) throws Exception {
    return httpSecurity
        .csrf(csrf -> csrf.disable())
        .authorizeHttpRequests(authRequest -> authRequest
            .requestMatchers("/api/v1/auth/**").permitAll()
            // .requestMatchers("/api/v1/income/**").hasAnyRole(Role.ADMIN.name(),
            // Role.MANAGER.name())
            .requestMatchers(HttpMethod.GET,
                "/api/v1/income/**")
            .hasAuthority(Permission.ADMIN_READ.name())
            .requestMatchers(HttpMethod.POST,
                "/api/v1/income/**")
            .hasAuthority(Permission.ADMIN_CREATE.name())
            .requestMatchers(HttpMethod.PUT,
                "/api/v1/income/**")
            .hasAuthority(Permission.ADMIN_UPDATE.name())
            .anyRequest().authenticated())
        .sessionManagement(
            sessionManager -> sessionManager
                .sessionCreationPolicy(SessionCreationPolicy.STATELESS))
        .authenticationProvider(authenticationProvider)
        .addFilterBefore(jwtFilter, UsernamePasswordAuthenticationFilter.class)
        .build();
  }

}
