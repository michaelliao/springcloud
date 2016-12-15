package com.feiyangedu.springcloud.petstore.common.rest;

import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;

import feign.codec.ErrorDecoder;

@Configuration
public class CustomFeignClientConfiguration {

	@Bean
	public ErrorDecoder feignErrorDecoder() {
		return new RestErrorDecoder();
	}

}
