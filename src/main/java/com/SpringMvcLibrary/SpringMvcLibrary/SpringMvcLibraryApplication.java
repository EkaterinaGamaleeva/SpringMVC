package com.SpringMvcLibrary.SpringMvcLibrary;

import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.EnableAutoConfiguration;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.context.annotation.Profile;
import org.springframework.data.jpa.repository.config.EnableJpaRepositories;

@Profile("!test")
@SpringBootApplication
@EnableJpaRepositories()
@EnableAutoConfiguration
public class SpringMvcLibraryApplication {

	public static void main(String[] args) {
		SpringApplication.run(SpringMvcLibraryApplication.class, args);
	}

}
