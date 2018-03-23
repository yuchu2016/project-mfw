package com.yuchu.projectmfw;

import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.cloud.netflix.feign.EnableFeignClients;
import org.springframework.data.jpa.repository.config.EnableJpaAuditing;

@SpringBootApplication
@EnableJpaAuditing
@EnableFeignClients
public class ProjectMfwApplication {

	public static void main(String[] args) {
		SpringApplication.run(ProjectMfwApplication.class, args);
	}
}
