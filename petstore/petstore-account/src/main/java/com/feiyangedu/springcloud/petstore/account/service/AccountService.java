package com.feiyangedu.springcloud.petstore.account.service;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageRequest;
import org.springframework.data.domain.Sort;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;

import com.feiyangedu.springcloud.petstore.account.bean.UserBean;
import com.feiyangedu.springcloud.petstore.account.domain.User;
import com.feiyangedu.springcloud.petstore.account.repository.UserRepository;
import com.feiyangedu.springcloud.petstore.common.context.UserContext;
import com.feiyangedu.springcloud.petstore.common.context.UserInfo;
import com.feiyangedu.springcloud.petstore.common.service.AbstractService;
import com.feiyangedu.springcloud.petstore.common.util.BeanUtil;

@RestController
@RequestMapping("/api/account")
public class AccountService extends AbstractService {

	@Autowired
	UserRepository userRepository;

	/**
	 * Get all users.
	 * 
	 * @param pageIndex
	 */
	@GetMapping("/users")
	public Page<User> getUsers(@RequestParam(value = "page", defaultValue = "0") int pageIndex) {
		return userRepository.findAll(new PageRequest(pageIndex, DEFAULT_PAGE_SIZE,
				new Sort(new Sort.Order(Sort.Direction.DESC, "createdAt"))));
	}

	@GetMapping("/users/{id}")
	public User getUser(@PathVariable("id") String id) {
		return checkNonNull(userRepository.findOne(id));
	}

	@PostMapping("/users")
	public User createUser(@RequestBody UserBean bean) {
		try (UserContext ctx = new UserContext(UserInfo.SYSTEM)) {
			User user = new User();
			BeanUtil.copyPublicFields(bean, user);
			userRepository.save(user);
			return user;
		}
	}
}
