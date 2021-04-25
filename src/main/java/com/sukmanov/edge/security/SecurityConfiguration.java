package com.sukmanov.edge.security;

import com.sukmanov.configuration.security.JwtConfig;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;

@Configuration
public class SecurityConfiguration {

    @Bean
    public JwtConfig jwtConfig() { return new JwtConfig(); }
}
