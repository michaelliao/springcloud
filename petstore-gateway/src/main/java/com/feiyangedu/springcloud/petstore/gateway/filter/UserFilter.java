package com.feiyangedu.springcloud.petstore.gateway.filter;

import java.nio.charset.StandardCharsets;
import java.util.Base64;

import javax.servlet.http.HttpServletRequest;

import org.apache.commons.logging.Log;
import org.apache.commons.logging.LogFactory;
import org.springframework.stereotype.Component;

import com.feiyangedu.springcloud.petstore.common.context.UserInfo;
import com.feiyangedu.springcloud.petstore.common.util.CookieUtil;
import com.feiyangedu.springcloud.petstore.common.util.JsonUtil;
import com.netflix.zuul.ZuulFilter;
import com.netflix.zuul.context.RequestContext;

/**
 * Get user info from cookie.
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
		RequestContext ctx = RequestContext.getCurrentContext();
		HttpServletRequest request = ctx.getRequest();
		UserInfo user = null;
		String s = CookieUtil.getAuthTokenFromCookie(request);
		if (s != null) {
			try {
				String decoded = new String(Base64.getUrlDecoder().decode(s), StandardCharsets.UTF_8);
				user = JsonUtil.readJson(decoded, UserInfo.class);
			} catch (Exception e) {
				log.warn("Invalid token", e);
				// TODO: delete cookie:
			}
		}
		if (user != null) {
			log.info("set user: " + user.toString());
			String str = JsonUtil.writeJson(user);
			ctx.addZuulRequestHeader("X-USER-INFO",
					Base64.getUrlEncoder().encodeToString(str.getBytes(StandardCharsets.UTF_8)));
		} else {
			log.info("set user = null.");
		}
		return null;
	}

	@Override
	public int filterOrder() {
		return 1;
	}

}
