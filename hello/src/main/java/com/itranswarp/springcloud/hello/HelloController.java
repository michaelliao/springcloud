package com.itranswarp.springcloud.hello;

import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.ResponseBody;
import org.springframework.web.bind.annotation.RestController;

/**
 * Hello controller.
 * 
 * @author liaoxuefeng
 */
@RestController
public class HelloController {

	@GetMapping("/")
	@ResponseBody
	String home() {
		return "<h1>Hello World!</h1>";
	}

	@GetMapping("/api")
	public String[] api() {
		return "Hello Spring Boot Application".split(" ");
	}
}
