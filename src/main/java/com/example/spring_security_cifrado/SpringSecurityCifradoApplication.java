package com.example.spring_security_cifrado;

import org.springframework.boot.CommandLineRunner;
import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.context.ApplicationContext;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Primary;
import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;
import org.springframework.security.crypto.password.PasswordEncoder;

@SpringBootApplication
public class SpringSecurityCifradoApplication {

	public static void main(String[] args) {

		SpringApplication.run(SpringSecurityCifradoApplication.class, args);
	}
		@Bean
		CommandLineRunner initData(UserRepository repository, PasswordEncoder bcryptEncoder, PasswordEncoder argon2Encoder) {
			return args -> {
				// 1. Ciframos la contraseña usando el encoder inyectado
				String rawPassword = "mi_password_seguro";

				//Generamos hashes

				String hashBcrypt = bcryptEncoder.encode(rawPassword);
				String hashArgon2 = argon2Encoder.encode(rawPassword);

				System.out.println("\n=== LABORATORIO DE CIFRADO ===");
				System.out.println("Password Original: " + rawPassword);
				System.out.println("BCrypt Hash: " + hashBcrypt);
				System.out.println("Argon2 Hash: " + hashArgon2);

				// Guardamos con Algoritmo Argon2 en la BD (PostgreSQL)
				User user = new User(null, "admin_user", hashArgon2);
				repository.save(user);
				System.out.println("Usuario guardado con Argon2 en PostgreSQL.");

				// Guardamos con Algoritmo Bcrypt en la BD (PostgreSQL)
				User userb = new User(null, "guest_user", hashBcrypt);
				repository.save(userb);
				System.out.println("Usuario guardado con Argon2 en PostgreSQL.");
			};
		}
}

