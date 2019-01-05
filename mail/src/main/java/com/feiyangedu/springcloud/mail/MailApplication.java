package com.feiyangedu.springcloud.mail;

import org.springframework.amqp.core.Binding;
import org.springframework.amqp.core.BindingBuilder;
import org.springframework.amqp.core.Queue;
import org.springframework.amqp.core.TopicExchange;
import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.context.annotation.Bean;
import org.springframework.web.servlet.config.annotation.ResourceHandlerRegistry;
import org.springframework.web.servlet.config.annotation.WebMvcConfigurer;

/**
 * Spring Boot Application using Spring Mail.
 * 
 * @author Michael Liao
 */
@SpringBootApplication
public class MailApplication {

	// Queue constants:
	public static final String QUEUE_NAME = "mail";
	public static final String EXCHANGE = "exchange";

	@Bean
	public Queue queue() {
		// durable = false, autoDelete = true
		return new Queue(QUEUE_NAME, false, false, true);
	}

	@Bean
	TopicExchange exchange() {
		// durable = false, autoDelete = true
		return new TopicExchange(EXCHANGE, false, true);
	}

	@Bean
	Binding binding(Queue queue, TopicExchange exchange) {
		return BindingBuilder.bind(queue).to(exchange).with(QUEUE_NAME);
	}

	@Bean
	public WebMvcConfigurer webMvcConfigurer() {
		/**
		 * Spring默认把静态资源文件/static/abc.js映射到/abc.js，不利于配置反向代理。配置为保留/static/前缀
		 */
		return new WebMvcConfigurer() {
			@Override
			public void addResourceHandlers(ResourceHandlerRegistry registry) {
				addResourceHandlers(registry);
				registry.addResourceHandler("/static/**").addResourceLocations("classpath:/static/");
			}
		};
	}

	public static void main(String[] args) throws Exception {
		SpringApplication.run(MailApplication.class, args);
	}

}
