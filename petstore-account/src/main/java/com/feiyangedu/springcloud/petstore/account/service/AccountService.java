package com.feiyangedu.springcloud.petstore.account.service;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageRequest;
import org.springframework.data.domain.Sort;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;

import com.feiyangedu.springcloud.petstore.account.bean.UserBean;
import com.feiyangedu.springcloud.petstore.account.domain.User;
import com.feiyangedu.springcloud.petstore.account.repository.UserRepository;
import com.feiyangedu.springcloud.petstore.common.service.AbstractService;
import com.feiyangedu.springcloud.petstore.common.util.BeanUtil;

@RestController
public class AccountService extends AbstractService {

	@Autowired
	UserRepository userRepository;

	/**
	 * Get all users.
	 * 
	 * @param pageIndex
	 */
	@GetMapping("/api/users")
	public Page<User> getUsers(@RequestParam(value = "page", defaultValue = "0") int pageIndex) {
		return userRepository.findAll(new PageRequest(pageIndex, DEFAULT_PAGE_SIZE,
				new Sort(new Sort.Order(Sort.Direction.DESC, "createdAt"))));
	}

	@GetMapping("/api/users/{id}")
	public User getUser(@PathVariable("id") String id) {
		return checkNonNull(userRepository.findOne(id));
	}

	@PostMapping("/api/users2")
	public User createUser2(@RequestParam("aa") java.time.LocalDateTime aa, @RequestBody UserBean bean) {
		User user = new User();
		BeanUtil.copyPublicFields(bean, user);
		userRepository.save(user);
		return user;
	}

	@PostMapping("/api/users")
	public User createUser(@RequestBody UserBean bean) {
		User user = new User();
		BeanUtil.copyPublicFields(bean, user);
		userRepository.save(user);
		return user;
	}
}
