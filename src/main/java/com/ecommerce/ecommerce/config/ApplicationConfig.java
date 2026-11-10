package com.ecommerce.ecommerce.config;

import com.ecommerce.ecommerce.entity.Utente;
import com.ecommerce.ecommerce.repository.UtenteRepository;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.security.authentication.AuthenticationManager;
import org.springframework.security.config.annotation.authentication.configuration.AuthenticationConfiguration;
import org.springframework.security.core.userdetails.UserDetailsService;
import org.springframework.security.core.userdetails.UsernameNotFoundException;
import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;
import org.springframework.security.crypto.password.PasswordEncoder;

import java.util.Optional;

@Configuration
public class ApplicationConfig {

    private final UtenteRepository utenteRepository;

    public ApplicationConfig(UtenteRepository utenteRepository) {
        this.utenteRepository = utenteRepository;
    }

    @Bean
    public UserDetailsService userDetailsService() {
        return new UserDetailsService() {
            @Override
            public Utente loadUserByUsername(String username) throws UsernameNotFoundException {
                Utente utente = utenteRepository.findByEmail(username);
                if (utente == null) {
                    throw new UsernameNotFoundException("Email non esistente");
                }
                return utente;

            }
        };
    }
    @Bean
    public AuthenticationManager authenticationManager(AuthenticationConfiguration config) throws Exception {
        return config.getAuthenticationManager();
    }
    @Bean
    public PasswordEncoder passwordEncoder() {
        return new BCryptPasswordEncoder();
    }
}