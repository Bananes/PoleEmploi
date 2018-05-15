package com.poleemploi.back.spring;

import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.context.annotation.ComponentScan;
import org.springframework.context.annotation.Configuration;

@SpringBootApplication
@Configuration
@ComponentScan("com.poleemploi.back")
public class PoleEmploiApplication {

	public static void main(String[] args) {
		SpringApplication.run(PoleEmploiApplication.class, args);
	}
}
