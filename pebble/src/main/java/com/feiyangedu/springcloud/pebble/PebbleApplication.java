package com.feiyangedu.springcloud.pebble;

import org.springframework.beans.factory.annotation.Value;
import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.context.annotation.Bean;
import org.springframework.web.servlet.config.annotation.ResourceHandlerRegistry;
import org.springframework.web.servlet.config.annotation.WebMvcConfigurer;
import org.springframework.web.servlet.config.annotation.WebMvcConfigurerAdapter;

import com.mitchellbosecke.pebble.PebbleEngine;
import com.mitchellbosecke.pebble.loader.ClasspathLoader;
import com.mitchellbosecke.pebble.spring4.PebbleViewResolver;

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
	 * 使用Pebble作为ViewEngine
	 */
	@Bean
	public PebbleViewResolver pebbleViewResolver() {
		PebbleViewResolver viewResolver = new PebbleViewResolver();
		viewResolver.setPrefix("templates/");
		viewResolver.setSuffix(".html");
		viewResolver.setPebbleEngine(
				new PebbleEngine.Builder().cacheActive(pebbleCache).loader(new ClasspathLoader()).build());
		return viewResolver;
	}

	@Bean
	public WebMvcConfigurer webMvcConfigurer() {
		/**
		 * Spring默认把静态资源文件/static/abc.js映射到/abc.js，不利于配置反向代理。配置为保留/static/前缀
		 */
		return new WebMvcConfigurerAdapter() {
			@Override
			public void addResourceHandlers(ResourceHandlerRegistry registry) {
				super.addResourceHandlers(registry);
				registry.addResourceHandler("/static/**").addResourceLocations("classpath:/static/");
			}
		};
	}

	public static void main(String[] args) throws Exception {
		SpringApplication.run(PebbleApplication.class, args);
	}

}
