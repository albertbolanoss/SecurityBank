package com.eazybytes.springsecuritybasic;

import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.context.annotation.ComponentScan;
import org.springframework.context.annotation.ComponentScans;

@SpringBootApplication
@ComponentScans({
	@ComponentScan("com.eazybytes.springsecuritybasic.configuration"),
	@ComponentScan("com.eazybytes.springsecuritybasic.controller")
})
public class SpringSecurityBasicApplication {
	public static void main(String[] args) {
		SpringApplication.run(SpringSecurityBasicApplication.class, args);
	}
}
