package com.feiyangedu.springcloud.petstore.web;

import org.springframework.beans.factory.annotation.Value;
import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.cloud.netflix.feign.EnableFeignClients;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Import;

import com.feiyangedu.springcloud.petstore.common.config.CustomWebConfiguration;
import com.mitchellbosecke.pebble.PebbleEngine;
import com.mitchellbosecke.pebble.loader.ClasspathLoader;
import com.mitchellbosecke.pebble.spring4.PebbleViewResolver;

@EnableFeignClients
@SpringBootApplication
@Import(CustomWebConfiguration.class)
public class WebApplication {

	public static void main(String[] args) {
		SpringApplication.run(WebApplication.class, args);
	}

	@Value("${pebble.cache:false}")
	boolean pebbleCache;

	@Bean
	public PebbleViewResolver pebbleViewResolver() {
		PebbleViewResolver viewResolver = new PebbleViewResolver();
		viewResolver.setPrefix("templates/");
		viewResolver.setSuffix(".html");
		viewResolver.setPebbleEngine(new PebbleEngine.Builder().loader(new ClasspathLoader()).autoEscaping(true)
				.cacheActive(pebbleCache).build());
		return viewResolver;
	}
}
