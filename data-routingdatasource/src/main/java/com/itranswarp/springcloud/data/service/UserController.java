package com.itranswarp.springcloud.data.service;

import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RestController;

import com.itranswarp.springcloud.data.context.RoutingDataSourceContext;
import com.itranswarp.springcloud.data.context.RoutingWith;
import com.itranswarp.springcloud.data.domain.User;
import com.itranswarp.springcloud.data.request.UserReq;

/**
 * User service which uses primary data source.
 * 
 * @author Michael Liao
 */
@RestController
public class UserController {

	@Autowired
	UserService userService;

	@PostMapping("/api/users")
	public User createUser(@RequestBody UserReq req) {
		return userService.createUser(req);
	}

	@GetMapping("/api/users")
	@RoutingWith(RoutingDataSourceContext.SLAVE)
	public List<User> getUsers() {
		return userService.getUsers();
	}

	@GetMapping("/api/users/{id}")
	@RoutingWith(RoutingDataSourceContext.SLAVE)
	public User getUser(@PathVariable("id") String id) {
		return userService.getUser(id);
	}

}
