package com.finanzas_personales.FinanzasPersonales;

import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.scheduling.annotation.EnableScheduling;

@EnableScheduling
@SpringBootApplication
public class FinanzasPersonalesApplication {

	public static void main(String[] args) {
		SpringApplication.run(FinanzasPersonalesApplication.class, args);
	}

}
