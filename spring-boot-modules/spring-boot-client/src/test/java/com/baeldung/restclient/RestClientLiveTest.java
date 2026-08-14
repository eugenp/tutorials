package com.baeldung.restclient;

import org.junit.jupiter.api.AfterEach;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.boot.web.server.test.LocalServerPort;
import org.springframework.core.ParameterizedTypeReference;
import org.springframework.http.HttpStatusCode;
import org.springframework.http.MediaType;
import org.springframework.http.ResponseEntity;
import org.springframework.http.client.ClientHttpRequestInterceptor;
import org.springframework.http.converter.json.JacksonJsonHttpMessageConverter;
import org.springframework.web.client.ApiVersionInserter;
import org.springframework.web.client.RestClient;
import tools.jackson.core.type.TypeReference;
import tools.jackson.databind.SerializationFeature;
import tools.jackson.databind.json.JsonMapper;

import java.util.HashMap;
import java.util.List;
import java.util.Map;

import static org.assertj.core.api.Assertions.assertThat;
import static org.assertj.core.api.Assertions.assertThatThrownBy;

@SpringBootTest(webEnvironment = SpringBootTest.WebEnvironment.RANDOM_PORT)
class RestClientLiveTest {

    @LocalServerPort
    private int port;
    private String uriBase;
    RestClient restClient = RestClient.create();

    @Autowired
    JsonMapper jsonMapper;

    @BeforeEach
    void setup() {
        uriBase = "http://localhost:" + port;
    }

    @AfterEach
    void teardown() {
        restClient.delete()
            .uri(uriBase + "/articles")
            .retrieve()
            .toBodilessEntity();
    }

    @Test
    void whenSavedArticleFetchedAsString_thenCorrectValueReturned() {
        Article article = new Article(1, "How to use RestClient");
        restClient.post()
            .uri(uriBase + "/articles")
            .contentType(MediaType.APPLICATION_JSON)
            .body(article)
            .retrieve()
            .toBodilessEntity();

        String articlesAsString = restClient.get()
            .uri(uriBase + "/articles")
            .retrieve()
            .body(String.class);

        assertThat(articlesAsString)
            .isEqualToIgnoringWhitespace("""
                [{"id":1,"title":"How to use RestClient"}]
            """);
    }

    @Test
    void whenArticleFetchedById_thenCorrectArticleReturned() {
        int id = 1;
        Article article = new Article(id, "How to use RestClient");
        restClient.post()
            .uri(uriBase + "/articles")
            .contentType(MediaType.APPLICATION_JSON)
            .body(article)
            .retrieve()
            .toBodilessEntity();

        Article fetchedArticle = restClient.get()
            .uri(uriBase + "/articles/" + id)
            .retrieve()
            .body(Article.class);

        assertThat(fetchedArticle)
            .usingRecursiveComparison()
            .isEqualTo(article);
    }

    @Test
    void whenArticlesFetchedAsParameterizedTypeReference_thenListReturned() {
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

        assertThat(articles)
            .hasSize(1)
            .first()
            .usingRecursiveComparison()
            .isEqualTo(article);
    }

    @Test
    void whenUsingCustomJsonMapper_thenArticleSerializedWithCustomFormat() {
        JsonMapper jsonMapper = JsonMapper.builder()
            .findAndAddModules()
            .enable(SerializationFeature.INDENT_OUTPUT)
            .build();

        RestClient customClient = restClient
            .mutate()
            .configureMessageConverters(converters -> converters
                .registerDefaults()
                .jsonMessageConverter(new JacksonJsonHttpMessageConverter(jsonMapper)))
            .build();

        int id = 1;
        Article article = new Article(id, "How to use RestClient");
        customClient.post()
            .uri(uriBase + "/articles")
            .contentType(MediaType.APPLICATION_JSON)
            .body(article)
            .retrieve()
            .toBodilessEntity();

        String fetchedArticle = customClient.get()
            .uri(uriBase + "/articles/" + id)
            .retrieve()
            .body(String.class);

        assertThat(fetchedArticle)
            .isEqualToIgnoringWhitespace("""
                {"id":1,"title":"How to use RestClient"}
            """);
    }

    @Test
    void whenUpdatingExistingArticle_thenArticleUpdatedSuccessfully() {
        int id = 1;
        Article article = new Article(id, "How to use RestClient");
        restClient.post()
            .uri(uriBase + "/articles")
            .contentType(MediaType.APPLICATION_JSON)
            .body(article)
            .retrieve()
            .toBodilessEntity();

        Article updatedArticle = new Article(id, "How to use RestClient even better");
        restClient.put()
            .uri(uriBase + "/articles/" + id)
            .contentType(MediaType.APPLICATION_JSON)
            .body(updatedArticle)
            .retrieve()
            .toBodilessEntity();

        List<Article> articles = restClient.get()
            .uri(uriBase + "/articles")
            .retrieve()
            .body(new ParameterizedTypeReference<>() {});

        assertThat(articles)
            .hasSize(1)
            .first()
            .usingRecursiveComparison()
            .isEqualTo(updatedArticle);
    }

    @Test
    void whenDeletingExistingArticle_thenArticleDeletedSuccessfully() {
        int id = 1;
        Article article = new Article(id, "How to use RestClient");
        restClient.post()
            .uri(uriBase + "/articles")
            .contentType(MediaType.APPLICATION_JSON)
            .body(article)
            .retrieve()
            .toBodilessEntity();

        restClient.delete()
            .uri(uriBase + "/articles/" + id)
            .retrieve()
            .toBodilessEntity();

        ResponseEntity<Void> fetchedArticleResponse = restClient.get()
            .uri(uriBase + "/articles")
            .accept(MediaType.APPLICATION_JSON)
            .retrieve()
            .toBodilessEntity();

        assertThat(fetchedArticleResponse.getStatusCode())
            .isEqualTo(HttpStatusCode.valueOf(204));
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

        assertThat(articles)
            .usingRecursiveComparison()
            .isEqualTo(List.of(article));
    }

    private List<Article> getArticlesWithExchange() {
        return restClient.get()
            .uri(uriBase + "/articles")
            .exchange((request, response) -> {
                if (response.getStatusCode().isSameCodeAs(HttpStatusCode.valueOf(204))) {
                    throw new ArticleNotFoundException();
                } else if (response.getStatusCode().isSameCodeAs(HttpStatusCode.valueOf(200))) {
                    return jsonMapper.readValue(response.getBody(), new TypeReference<>() {});
                } else {
                    throw new InvalidArticleResponseException();
                }
            });
    }

    @Test
    void shouldPostAndGetArticlesWithErrorHandling() {
        assertThatThrownBy(() -> {
            restClient
                .get()
                .uri(uriBase + "/articles/1234")
                .retrieve()
                .onStatus(
                    status -> status.value() == 404,
                    (request, response) -> {
                        throw new ArticleNotFoundException();
                    }
                )
                .body(new ParameterizedTypeReference<String>() {});
        }).isInstanceOf(ArticleNotFoundException.class);
    }

    @Test
    void whenUsingApiVersionInserter_thenVersionHeaderAddedToRequest() {
        RestClient versionedClient = restClient.mutate()
            .defaultApiVersion("2")
            .apiVersionInserter(ApiVersionInserter.useHeader("API-Version"))
            .build();

        Article fetchedArticle = versionedClient.get()
            .uri(uriBase + "/articles/" + 1)
            .retrieve()
            .body(Article.class);

        assertThat(fetchedArticle.getId())
            .isEqualTo(100);
        assertThat(fetchedArticle.getTitle())
            .isEqualTo("SECRET ARTICLE");
    }

    @Test
    void whenInterceptorSetsRequestAttribute_thenAttributeAvailableDuringExecution() {
        String key = "test-key";
        String value = "test-value";

        Map<String, Object> capturedAttributes = new HashMap<>();

        ClientHttpRequestInterceptor interceptor = (request, body, execution) -> {
            request.getAttributes().put(key, value);
            capturedAttributes.putAll(request.getAttributes());
            return execution.execute(request, body);
        };
        RestClient interceptedClient = restClient
            .mutate()
            .requestInterceptor(interceptor)
            .build();

        interceptedClient.get()
            .uri(uriBase + "/articles")
            .retrieve()
            .body(new ParameterizedTypeReference<List<Article>>() {});

        assertThat(capturedAttributes)
            .containsEntry(key, value);
    }

    @Test
    void whenSearchingArticleByTitle_thenCorrectArticleReturned() {
        Article article = new Article(1, "How to use RestClient");
        restClient.post()
            .uri(uriBase + "/articles")
            .contentType(MediaType.APPLICATION_JSON)
            .body(article)
            .retrieve()
            .toBodilessEntity();

        Article fetchedArticle = restClient.get()
            .uri(uriBuilder -> uriBuilder
                .scheme("http")
                .host("localhost")
                .port(port)
                .path("/articles/search")
                .queryParam("title", "RestClient")
                .build())
            .retrieve()
            .body(Article.class);

        assertThat(fetchedArticle)
            .usingRecursiveComparison()
            .isEqualTo(article);
    }

}
