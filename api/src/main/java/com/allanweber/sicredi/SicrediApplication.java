package com.allanweber.sicredi;

import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.scheduling.annotation.EnableScheduling;

@SpringBootApplication
@EnableScheduling
public class SicrediApplication {

	public static void main(String[] args) {
		SpringApplication.run(SicrediApplication.class, args);
	}

}
