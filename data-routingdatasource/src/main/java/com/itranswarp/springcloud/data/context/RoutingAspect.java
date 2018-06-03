package com.itranswarp.springcloud.data.context;

import org.aspectj.lang.ProceedingJoinPoint;
import org.aspectj.lang.annotation.Around;
import org.aspectj.lang.annotation.Aspect;
import org.springframework.stereotype.Component;

@Aspect
@Component
public class RoutingAspect {

	@Around("@annotation(routingWith)")
	public Object routingWithDataSource(ProceedingJoinPoint joinPoint, RoutingWith routingWith) throws Throwable {
		String key = routingWith.value();
		try (RoutingDataSourceContext ctx = new RoutingDataSourceContext(key)) {
			return joinPoint.proceed();
		}
	}
}
