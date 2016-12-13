package com.feiyangedu.springcloud.petstore.common.context;

import com.feiyangedu.springcloud.petstore.common.constant.Role;

public class UserInfo {

	public static final UserInfo SYSTEM;

	static {
		UserInfo sys = new UserInfo();
		sys.id = "00000000000000000000";
		sys.role = Role.ADMIN;
		sys.email = "system@sample.io";
		sys.name = "SYSTEM";
		SYSTEM = sys;
	}

	public String id;

	public int role;

	public String email;

	public String name;

	@Override
	public String toString() {
		return "UserInfo(id=" + id + ", email=" + email + ")";
	}
}
