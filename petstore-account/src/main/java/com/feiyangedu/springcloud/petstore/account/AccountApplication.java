package com.feiyangedu.springcloud.petstore.account;

import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.context.annotation.Import;
import org.springframework.data.jpa.repository.config.EnableJpaRepositories;

import com.feiyangedu.springcloud.petstore.common.config.CustomWebConfig;

@SpringBootApplication
@EnableJpaRepositories
@Import(CustomWebConfig.class)
public class AccountApplication {

	public static void main(String[] args) {
		SpringApplication.run(AccountApplication.class, args);
	}
}
