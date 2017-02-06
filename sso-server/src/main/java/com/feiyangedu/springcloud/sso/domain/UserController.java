package com.feiyangedu.springcloud.sso.domain;

import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.servlet.ModelAndView;

@Controller
public class UserController {

	@GetMapping("/")
	public ModelAndView index(@RequestParam(value = "name", defaultValue = "World") String name) {
		return new ModelAndView("index", "name", name);
	}

	@GetMapping("/hello/{name}")
	public ModelAndView hello(@PathVariable("name") String name) {
		User user = new User();
		user.setId("u" + Math.abs(name.hashCode()));
		user.setName(name);
		user.setEmail(name + " <" + name.toLowerCase() + "@example.com>");
		return new ModelAndView("hello", "user", user);
	}

}
