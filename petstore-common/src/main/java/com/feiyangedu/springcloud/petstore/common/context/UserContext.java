package com.feiyangedu.springcloud.petstore.common.context;

import java.util.ArrayList;
import java.util.List;

public class UserContext implements AutoCloseable {

	static final ThreadLocal<List<UserInfo>> threadLocal = new ThreadLocal<>();

	public static UserInfo getCurrentUserInfo() {
		List<UserInfo> list = threadLocal.get();
		return list == null || list.isEmpty() ? null : list.get(list.size() - 1);
	}

	public static UserInfo getRequiredCurrentUserInfo() {
		UserInfo userInfo = getCurrentUserInfo();
		if (userInfo == null) {
			throw new NoUserContextException();
		}
		return userInfo;
	}

	public UserContext(UserInfo userInfo) {
		List<UserInfo> list = threadLocal.get();
		if (list == null) {
			list = new ArrayList<>(3);
			threadLocal.set(list);
		}
		list.add(userInfo);
	}

	@Override
	public void close() {
		List<UserInfo> list = threadLocal.get();
		if (list.size() > 1) {
			list.remove(list.size() - 1);
		} else {
			threadLocal.remove();
		}
	}

}
