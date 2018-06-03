package com.feiyangedu.springcloud.petstore.gateway.filter;

import javax.servlet.http.HttpServletRequest;

import org.apache.commons.logging.Log;
import org.apache.commons.logging.LogFactory;
import org.springframework.stereotype.Component;

import com.netflix.zuul.ZuulFilter;
import com.netflix.zuul.context.RequestContext;

/**
 * Check ip blacklist.
 * 
 * @author Michael Liao
 */
@Component
public class IpFilter extends ZuulFilter {

	final Log log = LogFactory.getLog(getClass());

	@Override
	public String filterType() {
		return "pre";
	}

	@Override
	public boolean shouldFilter() {
		return true;
	}

	@Override
	public Object run() {
		RequestContext ctx = RequestContext.getCurrentContext();
		HttpServletRequest request = ctx.getRequest();
		log.info(String.format("%s: %s", request.getMethod(), request.getRequestURI()));
		return null;
	}

	@Override
	public int filterOrder() {
		return 1;
	}

}
