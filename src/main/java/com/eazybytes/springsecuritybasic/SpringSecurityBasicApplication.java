package com.eazybytes.springsecuritybasic;

import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.boot.autoconfigure.domain.EntityScan;
import org.springframework.context.annotation.ComponentScan;
import org.springframework.context.annotation.ComponentScans;
import org.springframework.data.jpa.repository.config.EnableJpaRepositories;

@SpringBootApplication
@ComponentScans({
	@ComponentScan("com.eazybytes.springsecuritybasic.configuration"),
	@ComponentScan("com.eazybytes.springsecuritybasic.controller")
})
@EnableJpaRepositories("com.eazybytes.springsecuritybasic.repository")
@EntityScan("com.eazybytes.springsecuritybasic.model")
public class SpringSecurityBasicApplication {
	public static void main(String[] args) {
		SpringApplication.run(SpringSecurityBasicApplication.class, args);
	}
}
