package com.phone.book;

import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.data.jpa.repository.config.EnableJpaAuditing;

@SpringBootApplication
@EnableJpaAuditing
public class PhoneBookAppApplication {

	public static void main(String[] args) {
		SpringApplication.run(PhoneBookAppApplication.class, args);
	}

}
