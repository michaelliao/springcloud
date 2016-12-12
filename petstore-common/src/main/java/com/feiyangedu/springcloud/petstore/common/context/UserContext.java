package com.feiyangedu.springcloud.petstore.common.context;

public class UserContext implements AutoCloseable {

	static final ThreadLocal<UserInfo> threadLocal = new ThreadLocal<>();

	public static UserInfo getCurrentUserInfo() {
		return threadLocal.get();
	}

	public static UserInfo getRequiredCurrentUserInfo() {
		UserInfo userInfo = getCurrentUserInfo();
		if (userInfo == null) {
			throw new NoUserContextException();
		}
		return userInfo;
	}

	public UserContext(UserInfo userInfo) {
		threadLocal.set(userInfo);
	}

	@Override
	public void close() {
		threadLocal.remove();
	}

}
