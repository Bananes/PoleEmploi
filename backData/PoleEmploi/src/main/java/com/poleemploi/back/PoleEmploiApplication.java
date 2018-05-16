package com.poleemploi.back;

import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.context.annotation.ComponentScan;

@SpringBootApplication
@ComponentScan("com.poleemploi.back")
public class PoleEmploiApplication {

	public static void main(String[] args) {
		SpringApplication.run(PoleEmploiApplication.class, args);
	}
}
