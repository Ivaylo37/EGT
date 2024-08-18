package com.example.egt;

import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.cache.annotation.EnableCaching;
import springfox.documentation.swagger2.annotations.EnableSwagger2;

@SpringBootApplication
@EnableCaching
@EnableSwagger2
public class EgtApplication {// http://localhost:8080/swagger-ui/

	public static void main(String[] args) {
		SpringApplication.run(EgtApplication.class, args);
	}

}
