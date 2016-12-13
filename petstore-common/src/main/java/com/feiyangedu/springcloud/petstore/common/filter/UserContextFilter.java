package com.feiyangedu.springcloud.petstore.common.filter;

import java.io.IOException;
import java.util.Base64;

import javax.servlet.Filter;
import javax.servlet.FilterChain;
import javax.servlet.FilterConfig;
import javax.servlet.ServletException;
import javax.servlet.ServletRequest;
import javax.servlet.ServletResponse;
import javax.servlet.http.HttpServletRequest;

import org.apache.commons.logging.Log;
import org.apache.commons.logging.LogFactory;

import com.fasterxml.jackson.databind.ObjectMapper;
import com.feiyangedu.springcloud.petstore.common.context.UserContext;
import com.feiyangedu.springcloud.petstore.common.context.UserInfo;

public class UserContextFilter implements Filter {

	ObjectMapper objectMapper;

	public void setObjectMapper(ObjectMapper objectMapper) {
		this.objectMapper = objectMapper;
	}

	final Log log = LogFactory.getLog(getClass());

	public UserContextFilter() {
	}

	@Override
	public void doFilter(ServletRequest req, ServletResponse resp, FilterChain chain)
			throws IOException, ServletException {
		HttpServletRequest request = (HttpServletRequest) req;
		String base64UserInfo = request.getHeader("X-User-Info");
		UserInfo userInfo = null;
		if (base64UserInfo != null) {
			try {
				byte[] data = Base64.getDecoder().decode(base64UserInfo);
				userInfo = objectMapper.readValue(data, 0, data.length, UserInfo.class);
			} catch (Exception e) {
				log.warn("Error decode X-User-Info.", e);
			}
		}
		log.info("Process request with user info: " + userInfo);
		try (UserContext ctx = new UserContext(userInfo)) {
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
