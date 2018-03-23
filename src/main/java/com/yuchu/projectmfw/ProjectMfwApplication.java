package com.yuchu.projectmfw;

import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.data.jpa.repository.config.EnableJpaAuditing;

@SpringBootApplication
@EnableJpaAuditing
public class ProjectMfwApplication {

	public static void main(String[] args) {
		SpringApplication.run(ProjectMfwApplication.class, args);
	}
}
