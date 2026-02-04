package com.example.spring_security_cifrado;

import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.context.annotation.Primary;
import org.springframework.security.config.annotation.web.configuration.EnableWebSecurity;
import org.springframework.security.crypto.argon2.Argon2PasswordEncoder;
import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;
import org.springframework.security.crypto.password.PasswordEncoder;

@Configuration
//@EnableWebSecurity // Opcional, pero recomendado para personalizar la seguridad
public class SecurityConfig {

    @Bean
    @Primary
    public PasswordEncoder bCryptEncoder(){
        return new BCryptPasswordEncoder(12); //Force 12
    }

    @Bean
    public PasswordEncoder argon2Encoder(){
        //saltLengt, hashLength, parallelism, memory, iteration
        return new Argon2PasswordEncoder(16, 32, 1, 65536,3);
    }
}

