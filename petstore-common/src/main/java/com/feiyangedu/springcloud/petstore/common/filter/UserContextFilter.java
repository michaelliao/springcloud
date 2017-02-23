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

import com.fasterxml.jackson.core.JsonParseException;
import com.fasterxml.jackson.databind.JsonMappingException;
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
		UserInfo user = null;
		HttpServletRequest request = (HttpServletRequest) req;
		String auth = request.getHeader("Authorization");
		if (auth != null && auth.startsWith("Bearer ")) {
			String token = auth.substring(7);
			try {
				byte[] data = Base64.getUrlDecoder().decode(token);
				user = objectMapper.readValue(data, UserInfo.class);
				log.info("Parsed user: " + user);
			} catch (IllegalArgumentException | JsonParseException | JsonMappingException e) {
				log.warn("Parse token failed: " + token);
			}
		} else {
			log.info("Invalid Authorization header. set user = null.");
		}
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
