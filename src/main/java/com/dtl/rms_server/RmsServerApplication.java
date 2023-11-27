package com.dtl.rms_server;

import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.scheduling.annotation.EnableScheduling;

@SpringBootApplication
@EnableScheduling
public class RmsServerApplication {

	public static void main(String[] args) {
		SpringApplication.run(RmsServerApplication.class, args);
	}

}
