package com.api.initialspring;

import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RestController;

@SpringBootApplication
@RestController
public class InitialSpringApplication {

	public static void main(String[] args) {
		SpringApplication.run(InitialSpringApplication.class, args);
	}

	@GetMapping("/")
	public String HelloWorld() {
		return "initialize Apllication";
	}

}
