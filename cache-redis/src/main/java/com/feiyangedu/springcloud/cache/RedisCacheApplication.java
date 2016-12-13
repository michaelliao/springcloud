package com.feiyangedu.springcloud.cache;

import java.util.UUID;
import java.util.concurrent.TimeUnit;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.cache.annotation.CacheEvict;
import org.springframework.cache.annotation.Cacheable;
import org.springframework.cache.annotation.EnableCaching;
import org.springframework.data.redis.core.StringRedisTemplate;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RestController;

/**
 * Spring Boot Application using Spring Cache.
 * 
 * @author Michael Liao
 */
@SpringBootApplication
@RestController
@EnableCaching
public class RedisCacheApplication {

	@Autowired
	StringRedisTemplate stringRedisTemplate;

	@GetMapping("/key/{key}")
	public String valueOf(@PathVariable("key") String key) {
		String value = stringRedisTemplate.opsForValue().get(key);
		if (value == null) {
			value = UUID.randomUUID().toString();
			stringRedisTemplate.opsForValue().set(key, value, 5, TimeUnit.SECONDS);
		}
		return value;
	}

	@GetMapping("/hi/{name}")
	public String hi(@PathVariable("name") String name) throws Exception {
		return slowHi(name);
	}

	@Cacheable("names")
	@GetMapping("/hello/{name}")
	public String hello(@PathVariable("name") String name) throws Exception {
		return slowHello(name);
	}

	@CacheEvict("names")
	@GetMapping("/remove/{name}")
	public boolean unhello(@PathVariable("name") String name) throws Exception {
		return true;
	}

	String slowHi(String name) throws Exception {
		Thread.sleep(3000);
		return "Hi, " + name + "!";
	}

	String slowHello(String name) throws Exception {
		Thread.sleep(3000);
		return "Hello, " + name + "!";
	}

	public static void main(String[] args) throws Exception {
		SpringApplication.run(RedisCacheApplication.class, args);
	}

}
