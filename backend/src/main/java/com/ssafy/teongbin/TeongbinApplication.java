package com.ssafy.teongbin;

import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.data.jpa.repository.config.EnableJpaAuditing;

@SpringBootApplication
@EnableJpaAuditing
public class TeongbinApplication {

	public static void main(String[] args) {
		SpringApplication.run(TeongbinApplication.class, args);
	}

}
