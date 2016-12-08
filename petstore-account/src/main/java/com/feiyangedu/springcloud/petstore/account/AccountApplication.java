package com.feiyangedu.springcloud.petstore.account;

import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.cloud.client.discovery.EnableDiscoveryClient;
import org.springframework.context.annotation.Import;
import org.springframework.data.jpa.repository.config.EnableJpaRepositories;

import com.feiyangedu.springcloud.petstore.common.config.SwaggerConfig;

@SpringBootApplication
@EnableJpaRepositories
@EnableDiscoveryClient
@Import(SwaggerConfig.class)
public class AccountApplication {

	public static void main(String[] args) {
		SpringApplication.run(AccountApplication.class, args);
	}
}
