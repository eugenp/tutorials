package com.baeldung.restclient;

import com.fasterxml.jackson.core.type.TypeReference;
import com.fasterxml.jackson.databind.ObjectMapper;
import org.junit.jupiter.api.AfterEach;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.boot.test.web.server.LocalServerPort;
import org.springframework.core.ParameterizedTypeReference;
import org.springframework.http.HttpStatusCode;
import org.springframework.http.MediaType;
import org.springframework.http.ResponseEntity;
import org.springframework.web.client.RestClient;

import java.util.List;

import static org.assertj.core.api.Assertions.assertThat;
import static org.assertj.core.api.Assertions.assertThatThrownBy;


@SpringBootTest(webEnvironment = SpringBootTest.WebEnvironment.RANDOM_PORT)
public class RestClientIntegrationTest {

	@LocalServerPort
	private int port;
	private String uriBase;
	RestClient restClient = RestClient.create();

	@Autowired
	ObjectMapper objectMapper;

	@BeforeEach
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

		assertThat(articlesAsString).isEqualTo("");
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
	void shouldPostAndGetArticlesWithExchange() {
		assertThatThrownBy(this::getArticlesWithExchange).isInstanceOf(ArticleNotFoundException.class);

		Article article = new Article(1, "How to use RestClient");
		restClient.post()
				.uri(uriBase + "/articles")
				.contentType(MediaType.APPLICATION_JSON)
				.body(article)
				.retrieve()
				.toBodilessEntity();

		List<Article> articles = getArticlesWithExchange();

		assertThat(articles).isEqualTo(List.of(article));
	}

	private List<Article> getArticlesWithExchange() {
        return restClient.get()
				.uri(uriBase + "/articles")
				.exchange((request, response) -> {
					if (response.getStatusCode().isSameCodeAs(HttpStatusCode.valueOf(204))) {
						throw new ArticleNotFoundException();
					} else if (response.getStatusCode().isSameCodeAs(HttpStatusCode.valueOf(200))) {
						return objectMapper.readValue(response.getBody(), new TypeReference<>() {});
					} else {
						throw new InvalidArticleResponseException();
					}
				});
	}

	@Test
	void shouldPostAndGetArticlesWithErrorHandling() {
		assertThatThrownBy(() -> {
				restClient.get()
						.uri(uriBase + "/articles/1234")
						.retrieve()
						.onStatus(status -> status.value() == 404, (request, response) -> { throw new ArticleNotFoundException(); })
						.body(new ParameterizedTypeReference<>() {});
		}).isInstanceOf(ArticleNotFoundException.class);
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

		ResponseEntity<Void> entity = restClient.get()
				.uri(uriBase + "/articles")
				.accept(MediaType.APPLICATION_JSON)
				.retrieve()
				.toBodilessEntity();

		assertThat(entity.getStatusCode()).isEqualTo(HttpStatusCode.valueOf(204));
	}
}
