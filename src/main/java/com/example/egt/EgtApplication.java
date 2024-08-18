package com.example.egt;

import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.cache.annotation.EnableCaching;

@SpringBootApplication
@EnableCaching
public class EgtApplication {// http://localhost:8080/swagger-ui/

	public static void main(String[] args) {
		SpringApplication.run(EgtApplication.class, args);
	}

}
