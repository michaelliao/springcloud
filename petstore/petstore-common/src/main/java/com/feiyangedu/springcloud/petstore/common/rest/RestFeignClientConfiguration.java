package com.feiyangedu.springcloud.petstore.common.rest;

import org.springframework.beans.factory.annotation.Value;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;

import feign.Logger;
import feign.codec.ErrorDecoder;

@Configuration
public class RestFeignClientConfiguration {

	@Value("${spring.feign.logger.level:BASIC}")
	String loggerLevel;

	@Bean
	public ErrorDecoder feignErrorDecoder() {
		return new RestErrorDecoder();
	}

	@Bean
	Logger.Level feignLoggerLevel() {
		return Logger.Level.valueOf(loggerLevel);
	}

}
