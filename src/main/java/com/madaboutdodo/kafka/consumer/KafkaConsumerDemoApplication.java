package com.madaboutdodo.kafka.consumer;

import java.util.ArrayList;
import java.util.List;

import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.boot.builder.SpringApplicationBuilder;
import org.springframework.boot.web.servlet.support.SpringBootServletInitializer;
import org.springframework.kafka.annotation.KafkaListener;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RestController;

@SpringBootApplication
@RestController
public class KafkaConsumerDemoApplication extends SpringBootServletInitializer {

	List<String> messages = new ArrayList<>();

	@Override
	protected SpringApplicationBuilder configure(SpringApplicationBuilder builder) {
		return builder.sources(KafkaConsumerDemoApplication.class);
	}

	public static void main(String[] args) {
		SpringApplication.run(KafkaConsumerDemoApplication.class, args);
	}

	@KafkaListener(topics = "foo", groupId = "consume-id1", containerFactory = "kafkaListenerContainerFactory")
	public List<String> consume(String message) {
		System.out.println("consumed message " + message);
		messages.add(message);
		return messages;
	}

	@GetMapping("/consume")
	public List<String> consumeMessage() {
		return messages;
	}
}
