package com.baeldung.spring.jms;

import static org.assertj.core.api.Assertions.assertThat;
import static org.awaitility.Awaitility.await;

import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.test.context.DynamicPropertyRegistry;
import org.springframework.test.context.DynamicPropertySource;
import org.testcontainers.activemq.ArtemisContainer;
import org.testcontainers.junit.jupiter.Container;
import org.testcontainers.junit.jupiter.Testcontainers;
import org.testcontainers.utility.DockerImageName;

import com.baeldung.spring.SampleApplication;
import com.baeldung.spring.jms.ArticlePublisher.Article;

@Testcontainers
@SpringBootTest(classes = SampleApplication.class)
class ArticleListenerLiveTest {

	@Container
	static ArtemisContainer activeMq = new ArtemisContainer(
				DockerImageName.parse("apache/activemq-artemis:2.37.0"))
			.withUser("admin")
			.withPassword("admin");

	@DynamicPropertySource
	static void configureProperties(DynamicPropertyRegistry registry) {
		registry.add("spring.artemis.broker-url", activeMq::getBrokerUrl);
	}

	@Autowired
	ArticlePublisher articlePublisher;

	@Autowired
	ArticleListener articleListener;

	@Test
	void shouldReceivePublishedArticle() {
		articlePublisher.publish("Foo", "John Doe");
		articlePublisher.publish("Bar", "Jane Doe");

		await().untilAsserted(() ->
				assertThat(articleListener.getReceivedArticles())
						.map(Article::title)
						.containsExactly("Foo", "Bar"));
	}

}
