package com.itranswarp.springcloud.data.context;

import java.lang.annotation.ElementType;
import java.lang.annotation.Retention;
import java.lang.annotation.RetentionPolicy;
import java.lang.annotation.Target;

@Target(ElementType.METHOD)
@Retention(RetentionPolicy.RUNTIME)
public @interface RoutingWith {

	/**
	 * The routing key as string.
	 * 
	 * @return Routing key.
	 */
	String value();
}
