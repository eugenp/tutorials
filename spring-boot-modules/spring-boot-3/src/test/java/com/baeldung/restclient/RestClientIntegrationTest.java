package com.baeldung.restclient;

import org.junit.jupiter.api.AfterEach;
import org.junit.jupiter.api.BeforeAll;
import org.junit.jupiter.api.Test;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.boot.test.web.server.LocalServerPort;
import org.springframework.core.ParameterizedTypeReference;
import org.springframework.http.MediaType;
import org.springframework.web.client.RestClient;

import java.util.List;

import static org.assertj.core.api.Assertions.assertThat;


@SpringBootTest(webEnvironment = SpringBootTest.WebEnvironment.RANDOM_PORT)
public class RestClientIntegrationTest {

	@LocalServerPort
	private int port;
	private String uriBase;
	RestClient restClient = RestClient.create();

	@BeforeAll
	public void setup() {
		uriBase = "http://localhost:" + port;
	}

	@AfterEach
	public void teardown() {
		restClient.delete()
				.uri(uriBase + "/articles")
				.retrieve()
				.toBodilessEntity();
	}

	@Test
	void shouldGetArticlesAndReturnString() {
		String articlesAsString = restClient.get()
				.uri(uriBase + "/articles")
				.retrieve()
				.body(String.class);

		assertThat(articlesAsString).isEqualTo("[]");
	}

	@Test
	void shouldPostAndGetArticles() {
		Article article = new Article(1, "How to use RestClient");
		restClient.post()
				.uri(uriBase + "/articles")
				.contentType(MediaType.APPLICATION_JSON)
				.body(article)
				.retrieve()
				.toBodilessEntity();

		List<Article> articles = restClient.get()
				.uri(uriBase + "/articles")
				.retrieve()
				.body(new ParameterizedTypeReference<>() {});

		assertThat(articles).isEqualTo(List.of(article));
	}

	@Test
	void shouldPostAndPutAndGetArticles() {
		Article article = new Article(1, "How to use RestClient");
		restClient.post()
				.uri(uriBase + "/articles")
				.contentType(MediaType.APPLICATION_JSON)
				.body(article)
				.retrieve()
				.toBodilessEntity();

		Article articleChanged = new Article(1, "How to use RestClient even better");
		restClient.put()
				.uri(uriBase + "/articles/1")
				.contentType(MediaType.APPLICATION_JSON)
				.body(articleChanged)
				.retrieve()
				.toBodilessEntity();

		List<Article> articles = restClient.get()
				.uri(uriBase + "/articles")
				.retrieve()
				.body(new ParameterizedTypeReference<>() {});

		assertThat(articles).isEqualTo(List.of(articleChanged));
	}

	@Test
	void shouldPostAndDeleteArticles() {
		Article article = new Article(1, "How to use RestClient");
		restClient.post()
				.uri(uriBase + "/articles")
				.contentType(MediaType.APPLICATION_JSON)
				.body(article)
				.retrieve()
				.toBodilessEntity();

		restClient.delete()
				.uri(uriBase + "/articles")
				.retrieve()
				.toBodilessEntity();

		List<Article> articles = restClient.get()
				.uri(uriBase + "/articles")
				.retrieve()
				.body(new ParameterizedTypeReference<>() {});

		assertThat(articles).isEqualTo(List.of());
	}
}
