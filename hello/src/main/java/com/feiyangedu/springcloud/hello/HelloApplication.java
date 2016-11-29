package com.feiyangedu.springcloud.hello;

import java.util.Arrays;
import java.util.List;

import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.ResponseBody;
import org.springframework.web.bind.annotation.RestController;

import springfox.documentation.swagger2.annotations.EnableSwagger2;

@SpringBootApplication
@RestController
@EnableSwagger2
public class HelloApplication {

	@RequestMapping(value = "/", method = RequestMethod.GET)
	@ResponseBody
	String home() {
		return "<h1>Hello World!</h1>";
	}

	@RequestMapping(value = "/api", method = RequestMethod.GET)
	public List<String> api() {
		return Arrays.asList("Hello", "Spring", "Boot");
	}

	public static void main(String[] args) throws Exception {
		SpringApplication.run(HelloApplication.class, args);
	}
}
