package com.backend.portfolio_ac.security;

import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;
import org.springframework.security.crypto.password.PasswordEncoder;

/**
 * Configuración para el codificador de contraseñas.
 * Proporciona un bean de PasswordEncoder utilizando BCrypt.
 *
 * @author bunnystring
 */
@Configuration
public class PasswordConfig {

    /**
     * Bean para codificar contraseñas usando BCrypt.
     *
     * @return implementacion de PasswordEncoder con BCrypt
     */
    @Bean
    public PasswordEncoder passwordEncoder(){
        return new BCryptPasswordEncoder();
    }
}