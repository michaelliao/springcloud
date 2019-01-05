package com.itranswarp.springcloud.pebble;

import org.springframework.beans.factory.annotation.Value;
import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.context.annotation.Bean;
import org.springframework.web.servlet.config.annotation.ResourceHandlerRegistry;
import org.springframework.web.servlet.config.annotation.WebMvcConfigurer;

import com.mitchellbosecke.pebble.PebbleEngine;
import com.mitchellbosecke.pebble.loader.ClasspathLoader;
import com.mitchellbosecke.pebble.spring.PebbleViewResolver;

/**
 * Spring Boot Application using Pebble.
 * 
 * @author Michael Liao
 */
@SpringBootApplication
public class PebbleApplication {

	@Value("${pebble.cache:false}")
	boolean pebbleCache;

	/**
	 * use Pebble as ViewEngine
	 */
	@Bean
	public PebbleViewResolver pebbleViewResolver() {
		PebbleEngine engine = new PebbleEngine.Builder().cacheActive(pebbleCache).loader(new ClasspathLoader()).build();
		PebbleViewResolver viewResolver = new PebbleViewResolver();
		viewResolver.setPrefix("templates/");
		viewResolver.setSuffix(".html");
		viewResolver.setPebbleEngine(engine);
		return viewResolver;
	}

	@Bean
	public WebMvcConfigurer webMvcConfigurer() {
		/**
		 * Keep /static/ prefix.
		 */
		return new WebMvcConfigurer() {
			@Override
			public void addResourceHandlers(ResourceHandlerRegistry registry) {
				registry.addResourceHandler("/static/**").addResourceLocations("classpath:/static/");
			}
		};
	}

	public static void main(String[] args) throws Exception {
		SpringApplication.run(PebbleApplication.class, args);
	}

}
