package com.feiyangedu.springcloud.petstore.common.context;

import com.feiyangedu.springcloud.petstore.account.domain.User;

public class UserContext implements AutoCloseable {

	static final ThreadLocal<User> threadLocal = new ThreadLocal<>();

	public static User getCurrentUser() {
		return threadLocal.get();
	}

	public static User getRequiredCurrentUser() {
		User user = getCurrentUser();
		if (user == null) {
			throw new NoUserContextException();
		}
		return user;
	}

	public UserContext(User user) {
		threadLocal.set(user);
	}

	@Override
	public void close() {
		threadLocal.remove();
	}

}
