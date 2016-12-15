package com.feiyangedu.springcloud.petstore.common.rest;

import org.springframework.beans.factory.annotation.Value;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;

import feign.Logger;
import feign.codec.ErrorDecoder;

@Configuration
public class CustomFeignClientConfiguration {

	@Value("spring.feign.logger.level:BASIC")
	Logger.Level loggerLevel;

	@Bean
	public ErrorDecoder feignErrorDecoder() {
		return new RestErrorDecoder();
	}

	@Bean
	Logger.Level feignLoggerLevel() {
		return loggerLevel;
	}
}
