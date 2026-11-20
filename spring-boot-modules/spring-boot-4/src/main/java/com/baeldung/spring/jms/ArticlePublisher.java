package com.baeldung.spring.jms;

import org.springframework.jms.core.JmsClient;
import org.springframework.stereotype.Component;

import lombok.RequiredArgsConstructor;
import lombok.SneakyThrows;
import lombok.extern.slf4j.Slf4j;

@Slf4j
@Component
@RequiredArgsConstructor
class ArticlePublisher {

	private final JmsClient jmsClient;

	@SneakyThrows
//	@EventListener(ApplicationReadyEvent.class)
//  Uncomment the above line to enable automatic publishing on application startup, for local testing
	void onApplicationReady() {
		Thread.sleep(5_000);
		publish("Understanding JMS in Spring Boot", "John Doe");
		publish("A Guide to Spring JMS", "Jane Smith");
	}


	public void publish(String title, String author) {
		var article = new Article(title, author);
		log.info("Publishing article: {}", article);

		jmsClient.destination("articles-queue")
				.send(article);
	}

	record Article(String title, String author) {
	}
}
