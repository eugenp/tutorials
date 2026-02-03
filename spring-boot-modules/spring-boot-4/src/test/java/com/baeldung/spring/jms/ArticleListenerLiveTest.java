package com.baeldung.spring.jms;

import static org.assertj.core.api.Assertions.assertThat;
import static org.awaitility.Awaitility.await;

import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.boot.testcontainers.service.connection.ServiceConnection;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.testcontainers.activemq.ArtemisContainer;
import org.testcontainers.utility.DockerImageName;

import com.baeldung.spring.SampleApplication;
import com.baeldung.spring.jms.ArticlePublisher.Article;

@SpringBootTest(classes = {SampleApplication.class, ArticleListenerLiveTest.TestConfig.class})
class ArticleListenerLiveTest {

	@Autowired
	ArticlePublisher articlePublisher;

	@Autowired
	ArticleListener articleListener;

	@Test
	void shouldReceivePublishedArticle() {
		articlePublisher.publish("Foo", "John Doe");
		articlePublisher.publish("Bar", "John Doe");

		await().untilAsserted(() -> assertThat(articleListener.getReceivedArticles()).map(
				Article::title).containsExactly("Foo", "Bar"));
	}

	@Configuration
	static class TestConfig {
		@Bean
		@ServiceConnection
		public ArtemisContainer activeMQ() {
			return new ArtemisContainer(
					DockerImageName.parse("apache/activemq-artemis:2.37.0"));
		}
	}
}
