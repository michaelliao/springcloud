package com.feiyangedu.springcloud.configbean;

import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.boot.context.properties.EnableConfigurationProperties;

import com.feiyangedu.springcloud.configbean.config.ProxyConfigs;

/**
 * Spring Boot Application.
 * 
 * @author Michael Liao
 */
@SpringBootApplication
@EnableConfigurationProperties(ProxyConfigs.class)
public class ConfigBeanApplication {

	public static void main(String[] args) throws Exception {
		SpringApplication.run(ConfigBeanApplication.class, args);
	}
}
