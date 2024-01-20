package com.cred4.credbackend;

import org.modelmapper.ModelMapper;
import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.context.annotation.Bean;
import org.springframework.web.servlet.config.annotation.CorsRegistry;
import org.springframework.web.servlet.config.annotation.WebMvcConfigurer;

@SpringBootApplication
public class CredBackendApplication {

	public static void main(String[] args) {
		SpringApplication.run(CredBackendApplication.class, args);
		System.out.println("====Server Stared====");
	}

	@Bean
	public ModelMapper getMapper(){
		return new ModelMapper();
	}

}
