package com.feiyangedu.springcloud.messaging;

import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.cloud.stream.annotation.EnableBinding;

/**
 * Spring Boot Application.
 * 
 * @author Michael Liao
 */
@SpringBootApplication
@EnableBinding({ VoteSink.class, VoteSource.class })
public class StreamApplication {

	public static void main(String[] args) throws Exception {
		SpringApplication.run(StreamApplication.class, args);
	}
}
