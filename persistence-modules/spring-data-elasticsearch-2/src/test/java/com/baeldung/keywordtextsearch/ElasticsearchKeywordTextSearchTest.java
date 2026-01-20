package com.baeldung.keywordtextsearch;

import static org.assertj.core.api.Assertions.assertThat;
import static org.awaitility.Awaitility.await;

import java.io.IOException;
import java.time.Duration;

import org.junit.jupiter.api.AfterEach;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.test.context.ContextConfiguration;
import org.springframework.test.context.DynamicPropertyRegistry;
import org.springframework.test.context.DynamicPropertySource;
import org.testcontainers.elasticsearch.ElasticsearchContainer;
import org.testcontainers.junit.jupiter.Container;
import org.testcontainers.junit.jupiter.Testcontainers;

import com.baeldung.wildcardsearch.ElasticsearchConfig;

import co.elastic.clients.elasticsearch.ElasticsearchClient;
import co.elastic.clients.elasticsearch._types.Refresh;
import co.elastic.clients.elasticsearch._types.query_dsl.Operator;
import co.elastic.clients.elasticsearch.core.IndexRequest;
import co.elastic.clients.elasticsearch.core.SearchResponse;
import co.elastic.clients.elasticsearch.indices.CreateIndexRequest;
import co.elastic.clients.elasticsearch.indices.DeleteIndexRequest;

/**
 * Integration tests for validating TEXT vs KEYWORD behavior using the document:
 *
 * {
 *   "type": "article",
 *   "title": "Using Elasticsearch with Spring Boot",
 *   "status": "IN_PROGRESS"
 * }
 *
 * Mapping:
 * - title  -> text (analyzed) + title.keyword (exact)
 * - type   -> keyword
 * - status -> keyword
 */
@SpringBootTest
@ContextConfiguration(classes = ElasticsearchConfig.class)
@Testcontainers
class ElasticsearchKeywordTextSearchTest {

    private static final Logger logger = LoggerFactory.getLogger(ElasticsearchKeywordTextSearchTest.class);

    private static final String TEST_INDEX = "test_articles";

    @Autowired
    private ElasticsearchClient elasticsearchClient;

    @Container
    static ElasticsearchContainer elasticsearchContainer = new ElasticsearchContainer("docker.elastic.co/elasticsearch/elasticsearch:8.11.1").withExposedPorts(
            9200)
        .withEnv("discovery.type", "single-node")
        .withEnv("xpack.security.enabled", "false")
        .withEnv("xpack.security.http.ssl.enabled", "false");

    @DynamicPropertySource
    static void configureProperties(DynamicPropertyRegistry registry) {
        registry.add("elasticsearch.host", elasticsearchContainer::getHost);
        registry.add("elasticsearch.port", () -> elasticsearchContainer.getMappedPort(9200));
    }

    /**
     * Document model used in tests.
     */
    public record Article(String type, String title, String status) {

    }

    @BeforeEach
    void setUp() throws IOException {
        createTestIndex();
        indexSampleDocuments();
        waitUntilDocumentsIndexed();
    }

    @AfterEach
    void cleanup() throws IOException {
        if (elasticsearchClient.indices()
            .exists(e -> e.index(TEST_INDEX))
            .value()) {
            elasticsearchClient.indices()
                .delete(DeleteIndexRequest.of(d -> d.index(TEST_INDEX)));
        }
    }

    @Test
    void whenMatchQueryOnTitleText_thenReturnDocument() throws IOException {
        SearchResponse<Article> response = elasticsearchClient.search(s -> s.index(TEST_INDEX)
            .query(q -> q.match(m -> m.field("title")
                .query("spring elasticsearch")
                .operator(Operator.And))), Article.class);

        assertThat(response.hits()
            .hits()).hasSize(2);

        Article article = response.hits()
            .hits()
            .get(0)
            .source();
        assertThat(article).isNotNull();
        assertThat(article.title()).isEqualTo("Spring Boot Elasticsearch Basics");

        logger.info("Match query returned: {}", article);
    }

    @Test
    void whenTermQueryOnKeywordFields_thenReturnDocument() throws IOException {
        SearchResponse<Article> response = elasticsearchClient.search(s -> s.index(TEST_INDEX)
            .query(q -> q.bool(b -> b.filter(f -> f.term(t -> t.field("type")
                    .value("article")))
                .filter(f -> f.term(t -> t.field("status")
                    .value("IN_PROGRESS"))))), Article.class);

        assertThat(response.hits()
            .hits()).hasSize(1);

        Article article = response.hits()
            .hits()
            .get(0)
            .source();
        assertThat(article).isNotNull();
        assertThat(article.type()).isEqualTo("article");
        assertThat(article.status()).isEqualTo("IN_PROGRESS");
    }

    @Test
    void whenTermQueryUsesDifferentCaseOnKeyword_thenNoMatchByDefault() throws IOException {
        SearchResponse<Article> response = elasticsearchClient.search(s -> s.index(TEST_INDEX)
            .query(q -> q.term(t -> t.field("status")
                .value("in_progress"))), Article.class);

        assertThat(response.hits()
            .hits()).isEmpty();
    }

    @Test
    void whenPerformAggregations_thenCountShouldBeReturned() throws IOException {
        SearchResponse<Void> response = elasticsearchClient.search(s -> s.index(TEST_INDEX)
            .size(0)
            .aggregations("by_status", a -> a.terms(t -> t.field("status"))), Void.class);

        response.aggregations()
            .get("by_status")
            .sterms()
            .buckets()
            .array()
            .forEach(b -> System.out.println(b.key()
                .stringValue() + " -> " + b.docCount()));

        assertThat(response.hits()
            .hits()).isEmpty();
    }

    private void waitUntilDocumentsIndexed() {
        await().atMost(Duration.ofSeconds(10))
            .pollInterval(Duration.ofMillis(100))
            .pollDelay(Duration.ofMillis(50))
            .untilAsserted(() -> {

                SearchResponse<Article> response = elasticsearchClient.search(s -> s.index(TEST_INDEX)
                    .query(q -> q.match(m -> m.field("title")
                        .query("spring"))), Article.class);

                assertThat(response.hits()
                    .hits()).as("Expected documents to be indexed")
                    .hasSizeGreaterThanOrEqualTo(2);
            });
    }

    private void createTestIndex() throws IOException {
        CreateIndexRequest createRequest = CreateIndexRequest.of(c -> c.index(TEST_INDEX)
            .mappings(m -> m.properties("type", p -> p.keyword(k -> k))
                .properties("status", p -> p.keyword(k -> k))
                .properties("title", p -> p.text(t -> t.fields("keyword", kf -> kf.keyword(k -> k))))));

        elasticsearchClient.indices()
            .create(createRequest);

        logger.debug("Created test index {} with mapping for text/keyword behavior", TEST_INDEX);
    }

    private void indexSampleDocuments() throws IOException {
        indexDocument("1", new Article("article", "Using Elasticsearch with Spring Boot", "IN_PROGRESS"));

        indexDocument("2", new Article("article", "Spring Boot Elasticsearch Basics", "DONE"));
    }

    private void indexDocument(String id, Article article) throws IOException {
        IndexRequest<Article> indexRequest = IndexRequest.of(i -> i.index(TEST_INDEX)
            .id(id)
            .document(article)
            .refresh(Refresh.True));

        elasticsearchClient.index(indexRequest);
    }
}
