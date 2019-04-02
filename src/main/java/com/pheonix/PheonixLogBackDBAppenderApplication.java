package com.pheonix;

import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.scheduling.annotation.EnableAsync;

@SpringBootApplication
@EnableAsync
public class PheonixLogBackDBAppenderApplication {

	public static void main(String[] args) {
		SpringApplication.run(PheonixLogBackDBAppenderApplication.class, args);
	}

}
