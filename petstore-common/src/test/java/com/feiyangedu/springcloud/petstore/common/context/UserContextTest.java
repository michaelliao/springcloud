package com.feiyangedu.springcloud.petstore.common.context;

import static org.junit.Assert.*;

import org.junit.Before;
import org.junit.Test;

public class UserContextTest {

	UserInfo USER_A;
	UserInfo USER_B;
	UserInfo USER_C;

	@Before
	public void setUp() throws Exception {
		USER_A = createUserInfo("A");
		USER_B = createUserInfo("B");
		USER_C = createUserInfo("C");
	}

	@Test
	public void testGetCurrentUserInfoNull() {
		assertNull(UserContext.getCurrentUserInfo());
	}

	@Test
	public void testGetCurrentUserInfoNonNull() {
		try (UserContext ctx = new UserContext(USER_A)) {
			UserInfo current = UserContext.getCurrentUserInfo();
			assertNotNull(current);
			assertEquals(USER_A.name, current.name);
		}
		assertNull(UserContext.getCurrentUserInfo());
	}

	@Test
	public void testGetCurrentUserInfoNested() {
		try (UserContext ctx = new UserContext(USER_A)) {
			UserInfo current = UserContext.getCurrentUserInfo();
			assertNotNull(current);
			assertEquals(USER_A.name, current.name);
			try (UserContext ctx2 = new UserContext(USER_B)) {
				UserInfo nested = UserContext.getCurrentUserInfo();
				assertNotNull(nested);
				assertEquals(USER_B.name, nested.name);
				try (UserContext ctx3 = new UserContext(USER_C)) {
					UserInfo deep = UserContext.getCurrentUserInfo();
					assertNotNull(deep);
					assertEquals(USER_C.name, deep.name);
				}
				UserInfo nested2 = UserContext.getCurrentUserInfo();
				assertNotNull(nested2);
				assertEquals(USER_B.name, nested2.name);
			}
			UserInfo current2 = UserContext.getCurrentUserInfo();
			assertNotNull(current2);
			assertEquals(USER_A.name, current2.name);
		}
		assertNull(UserContext.getCurrentUserInfo());
	}

	@Test(expected = NoUserContextException.class)
	public void testGetRequiredCurrentUserInfo() {
		UserContext.getRequiredCurrentUserInfo();
	}

	UserInfo createUserInfo(String name) {
		UserInfo u = new UserInfo();
		u.id = "ID-" + name;
		u.name = name;
		u.email = name + "@test.com";
		u.role = 0;
		return u;
	}

}
