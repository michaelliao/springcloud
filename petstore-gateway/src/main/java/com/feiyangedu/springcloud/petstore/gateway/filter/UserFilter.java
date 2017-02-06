package com.feiyangedu.springcloud.petstore.gateway.filter;

import javax.servlet.http.HttpServletRequest;

import org.apache.commons.logging.Log;
import org.apache.commons.logging.LogFactory;
import org.springframework.stereotype.Component;

import com.netflix.zuul.ZuulFilter;
import com.netflix.zuul.context.RequestContext;

/**
 * Decode token as user info.
 * 
 * @author Michael Liao
 */
@Component
public class UserFilter extends ZuulFilter {

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
		log.info("check token...");
		try {
			Thread.sleep(1000);
		} catch (InterruptedException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
		log.info("done.");
		return null;
	}

	@Override
	public int filterOrder() {
		return 1;
	}

}
