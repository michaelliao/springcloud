package com.feiyangedu.springcloud.petstore.common.util;

import javax.servlet.http.Cookie;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import org.apache.commons.logging.Log;
import org.apache.commons.logging.LogFactory;

public interface CookieUtil {

	public final String AUTH_COOKIE_NAME = "auth";

	final Log log = LogFactory.getLog(CookieUtil.class);

	public static String getAuthTokenFromCookie(HttpServletRequest request) {
		Cookie[] cookies = request.getCookies();
		if (cookies != null) {
			for (Cookie cookie : cookies) {
				if (AUTH_COOKIE_NAME.equals(cookie.getName())) {
					String s = cookie.getValue();
					log.info("Found auth token from cookie: " + s);
					return s;
				}
			}
		}
		return null;
	}

	public static void removeAuthTokenFromCookie(HttpServletResponse response) {
		Cookie cookie = new Cookie(AUTH_COOKIE_NAME, "deleted");
		cookie.setMaxAge(0);
		cookie.setPath("/");
		cookie.setHttpOnly(true);
		response.addCookie(cookie);
	}
}
