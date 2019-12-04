package sukmanov.edge.security;

import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;

@Configuration
public class SecurityConfiguration {

    @Bean
    public JwtConfig JwtConfig() { return new JwtConfig(); }
}
