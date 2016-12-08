package com.feiyangedu.springcloud.petstore.account.service;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RestController;

import com.feiyangedu.springcloud.petstore.account.domain.User;
import com.feiyangedu.springcloud.petstore.account.repository.UserRepository;
import com.feiyangedu.springcloud.petstore.common.service.AbstractService;

@RestController
public class AccountService extends AbstractService {

	@Autowired
	UserRepository userRepository;

	@GetMapping("/api/users/{id}")
	public User getUser(@PathVariable("id") String id) {
		return checkNonNull(userRepository.findOne(id));
	}

}
