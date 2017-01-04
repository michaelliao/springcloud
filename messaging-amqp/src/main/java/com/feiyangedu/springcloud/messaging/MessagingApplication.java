package com.feiyangedu.springcloud.messaging;

import java.util.UUID;
import java.util.concurrent.atomic.AtomicLong;

import org.apache.commons.logging.Log;
import org.apache.commons.logging.LogFactory;
import org.springframework.amqp.core.AmqpTemplate;
import org.springframework.amqp.core.Binding;
import org.springframework.amqp.core.BindingBuilder;
import org.springframework.amqp.core.Queue;
import org.springframework.amqp.core.TopicExchange;
import org.springframework.amqp.rabbit.annotation.RabbitListener;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.context.annotation.Bean;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;

/**
 * Spring Boot Application.
 * 
 * @author Michael Liao
 */
@SpringBootApplication
@RestController
public class MessagingApplication {

	final Log log = LogFactory.getLog(getClass());
	final AtomicLong counter = new AtomicLong(0L);

	final static String QUEUE_NAME = "notify";
	final static String EXCHANGE = "exchange";

	@Autowired
	AmqpTemplate amqpTemplate;

	@Bean
	public Queue queue() {
		// durable = false:
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

	@RabbitListener(queues = QUEUE_NAME)
	public void processQueueMessage(String content) {
		long n = counter.incrementAndGet();
		String s = "\n+--------------------------------------------------+\n" //
				+ "|MESSAGE RECEIVED FROM QUEUE                       |\n" //
				+ "+--------------------------------------------------+\n" //
				+ "|%-50s|\n" //
				+ "+--------------------------------------------------+\n" //
				+ "|TOTAL RECEIVED: %-5d                             |\n" //
				+ "+--------------------------------------------------+\n";
		log.info(String.format(s, content, n));
	}

	@RequestMapping(value = "/send", method = RequestMethod.GET)
	String sendMessage(@RequestParam(name = "message", defaultValue = "") String message) {
		if (message.isEmpty()) {
			message = "Message<" + UUID.randomUUID().toString() + ">";
		}
		amqpTemplate.convertAndSend(EXCHANGE, QUEUE_NAME, message);
		return "Message sent ok!";
	}

	public static void main(String[] args) throws Exception {
		SpringApplication.run(MessagingApplication.class, args);
	}
}
