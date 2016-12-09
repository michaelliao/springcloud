package com.feiyangedu.springcloud.petstore.common.filter;

import java.io.IOException;

import javax.servlet.Filter;
import javax.servlet.FilterChain;
import javax.servlet.FilterConfig;
import javax.servlet.ServletException;
import javax.servlet.ServletRequest;
import javax.servlet.ServletResponse;

import org.apache.commons.logging.Log;
import org.apache.commons.logging.LogFactory;

import com.feiyangedu.springcloud.petstore.account.domain.User;
import com.feiyangedu.springcloud.petstore.common.context.UserContext;

public class UserContextFilter implements Filter {

	final Log log = LogFactory.getLog(getClass());

	public UserContextFilter() {
	}

	@Override
	public void doFilter(ServletRequest req, ServletResponse resp, FilterChain chain)
			throws IOException, ServletException {
		User user = User.SYSTEM;
		try (UserContext ctx = new UserContext(user)) {
			chain.doFilter(req, resp);
		}
	}

	@Override
	public void init(FilterConfig config) throws ServletException {
		log.info("Init UserContextFilter.");
	}

	@Override
	public void destroy() {
		log.info("Destroy UserContextFilter.");
	}

}
