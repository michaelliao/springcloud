package com.feiyangedu.springcloud.petstore.web.mvc;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RestController;

import com.feiyangedu.springcloud.petstore.web.client.AccountFeignClient;

import io.swagger.api.client.account.model.Page;
import io.swagger.api.client.account.model.User;

@RestController
public class UserController {

	@Autowired
	AccountFeignClient accountClient;

	@GetMapping("/getUsers")
	public Page<User> getUsers() {
		return accountClient.getUsers(0);
	}

	@GetMapping("/getUser/{id}")
	public User getUser(@PathVariable("id") String id) {
		return accountClient.getUser(id);
	}

	@GetMapping("/getUser2")
	public String getUser() {
		try {
			accountClient.getUser("1b65nvt1k56u106000pp");
		} catch (Exception e) {
			e.printStackTrace();
			return "ex";
		}
		return "ok";
	}
}
