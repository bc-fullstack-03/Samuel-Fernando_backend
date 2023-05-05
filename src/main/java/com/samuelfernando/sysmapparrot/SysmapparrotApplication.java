package com.samuelfernando.sysmapparrot;

import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.context.annotation.Bean;
import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;
import org.springframework.security.crypto.password.PasswordEncoder;

@SpringBootApplication
public class SysmapparrotApplication {

	public static void main(String[] args) {
		SpringApplication.run(SysmapparrotApplication.class, args);
	}

	@Bean
	public PasswordEncoder bcryptPasswordEncorder() {
		return new BCryptPasswordEncoder();
	}
}
