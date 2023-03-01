package com.felarca.ootp;

import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.boot.autoconfigure.security.servlet.SecurityAutoConfiguration;

@SpringBootApplication(exclude = { SecurityAutoConfiguration.class })
public class OotpApplication {

	public static void main(String[] args) {
		SpringApplication.run(OotpApplication.class, args);
	}

}
