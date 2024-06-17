package com.Java020.MonieFlex;

import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.data.jpa.repository.config.EnableJpaAuditing;

@SpringBootApplication
@EnableJpaAuditing
public class MonieFlexApplication {

	public static void main(String[] args) {
		SpringApplication.run(MonieFlexApplication.class, args);
	}

}
