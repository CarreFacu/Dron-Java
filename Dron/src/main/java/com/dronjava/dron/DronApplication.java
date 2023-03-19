package com.dronjava.dron;

import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.scheduling.annotation.EnableScheduling;

@SpringBootApplication
@EnableScheduling
public class DronApplication {

	public static void main(String[] args) {
		SpringApplication.run(DronApplication.class, args);
	}

}
