package com.feiyangedu.springcloud.petstore.web;

import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.context.annotation.Import;

import com.feiyangedu.springcloud.petstore.common.config.CustomWebConfig;

@SpringBootApplication
@Import(CustomWebConfig.class)
public class WebApplication {

	public static void main(String[] args) {
		SpringApplication.run(WebApplication.class, args);
	}
}
