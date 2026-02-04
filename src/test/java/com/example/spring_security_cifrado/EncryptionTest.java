package com.example.spring_security_cifrado;

import org.junit.jupiter.api.Test;
import org.springframework.security.crypto.argon2.Argon2PasswordEncoder;
import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;
import org.springframework.security.crypto.password.*;
import org.springframework.security.crypto.scrypt.SCryptPasswordEncoder;

import java.util.HashMap;
import java.util.Map;

public class EncryptionTest {

    /***
     * BCrypt que genera su propio salt de 16 bytes
     *
     * El resultado de cifrar con BCrypt será un String de 60 Caracteres
     *
     * $a versión
     * $10 fuerza (Valor que va de 4 a 31, por defecto está en el valor 10)
     *
     * Los siguientes 22 Caracteres son el salt generado.
     */
    @Test
    void bcryptTest(){

        BCryptPasswordEncoder passwordEncoder = new BCryptPasswordEncoder();

        String hashedPassword = passwordEncoder.encode("admin");

        System.out.println(hashedPassword);

        boolean matches = passwordEncoder.matches("adminZafwe", hashedPassword);
        System.out.println(matches);

    }

    @Test
    void bcryptCheckMultiplePasswords(){
        BCryptPasswordEncoder passwordEncoder =  new BCryptPasswordEncoder();
        for (int i = 0; i < 30; i++){
            System.out.println(passwordEncoder.encode("admin"));
        }
    }

    @Test
    void pbkdf2() {
        Pbkdf2PasswordEncoder passwordEncoder =  new Pbkdf2PasswordEncoder("secret", 16, 310000, Pbkdf2PasswordEncoder.SecretKeyFactoryAlgorithm.PBKDF2WithHmacSHA256);
        for (int i = 0; i < 30; i++){
            System.out.println(passwordEncoder.encode("admin"));
        }
    }

    @Test
    void argon() {
        Argon2PasswordEncoder passwordEncoder =  new Argon2PasswordEncoder(16, 32, 1, 65536, 3);
        for (int i = 0; i < 30; i++){
            System.out.println(passwordEncoder.encode("admin"));
        }
    }

    @Test
    void scrypt() {
        SCryptPasswordEncoder passwordEncoder =  new SCryptPasswordEncoder(16384, 8, 1, 32, 64);
        for (int i = 0; i < 30; i++){
            System.out.println(passwordEncoder.encode("admin"));
        }
    }

    @Test
    void springPasswordEncode(){

        Map<String, PasswordEncoder> encoders = new HashMap<>();
        encoders.put("bcrypt", new BCryptPasswordEncoder());
        //encoders.put("pbkdf2", new Pbkdf2PasswordEncoder());
        //encoders.put("argon2", new Argon2PasswordEncoder());
        //encoders.put("scrypt", new SCryptPasswordEncoder());

        //No seguros o depreciados:
        //encoders.put("noop", new NoOpPasswordEncoder());
        encoders.put("sha256", new StandardPasswordEncoder());

        PasswordEncoder passwordEncoder = new DelegatingPasswordEncoder("bcrypt", encoders);

        String hashedPassword = passwordEncoder.encode("admin");
        System.out.println(hashedPassword);
    }
}
