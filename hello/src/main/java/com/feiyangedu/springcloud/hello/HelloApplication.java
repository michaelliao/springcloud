package com.feiyangedu.springcloud.hello;

import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.ResponseBody;
import org.springframework.web.bind.annotation.RestController;

/**
 * Spring Boot Application.
 * 
 * @author Michael Liao
 */
@SpringBootApplication
@RestController
public class HelloApplication {

	@GetMapping("/")
	@ResponseBody
	String home() {
		return "<h1>Hello World!</h1>";
	}

	@GetMapping("/api")
	public String[] api() {
		return "Hello Spring Boot Application".split(" ");
	}

	public static void main(String[] args) throws Exception {
		SpringApplication.run(HelloApplication.class, args);
	}
}
