package com.baeldung.quarkus.elasticsearch;

import co.elastic.clients.elasticsearch.ElasticsearchClient;
import co.elastic.clients.elasticsearch._types.FieldValue;
import co.elastic.clients.elasticsearch._types.query_dsl.Query;
import co.elastic.clients.elasticsearch._types.query_dsl.QueryBuilders;
import co.elastic.clients.elasticsearch.core.DeleteRequest;
import co.elastic.clients.elasticsearch.core.IndexRequest;
import co.elastic.clients.elasticsearch.core.SearchRequest;
import co.elastic.clients.elasticsearch.core.SearchResponse;
import co.elastic.clients.elasticsearch.core.search.Hit;
import co.elastic.clients.elasticsearch.core.search.HitsMetadata;
import com.baeldung.quarkus.elasticsearch.model.StoreItem;
import io.quarkus.test.junit.QuarkusTest;
import io.vertx.core.json.JsonArray;
import io.vertx.core.json.JsonObject;
import jakarta.inject.Inject;
import org.apache.http.util.EntityUtils;
import org.elasticsearch.client.Request;
import org.elasticsearch.client.Response;
import org.elasticsearch.client.RestClient;
import org.junit.jupiter.api.AfterEach;
import org.junit.jupiter.api.Test;

import java.io.IOException;
import java.util.ArrayList;
import java.util.List;
import java.util.Objects;
import java.util.UUID;
import java.util.stream.Collectors;
import java.util.stream.Stream;

import static org.assertj.core.api.Assertions.assertThat;

/**
 * This Manual test requires: Elasticsearch instance running on localhost:9200.
 * <p>
 * The following docker command can be used: docker run -d  -p 9200:9200 -e "discovery.type=single-node" elasticsearch:7.17.11
 */
@QuarkusTest
class ElasticsearchManualTest {

    @Inject
    RestClient restClient;

    @Inject
    ElasticsearchClient elasticsearchClient;
    private StoreItem iosPhone;
    private StoreItem androidPhone;

    private void indexUsingElasticsearchClient() throws IOException, InterruptedException {
        androidPhone = new StoreItem();
        androidPhone.setId(UUID.randomUUID().toString());
        androidPhone.setPrice(500L);
        androidPhone.setName("Android smartphone");

        IndexRequest<StoreItem> request = IndexRequest.of(
          b -> b.index("store-items")
            .id(androidPhone.getId())
            .document(androidPhone));

        elasticsearchClient.index(request);
        Thread.sleep(1000);
    }

    private void indexUsingRestClient() throws IOException, InterruptedException {
        iosPhone = new StoreItem();
        iosPhone.setId(UUID.randomUUID().toString());
        iosPhone.setPrice(1000L);
        iosPhone.setName("IOS smartphone");

        Request restRequest = new Request(
          "PUT",
          "/store-items/_doc/" + iosPhone.getId());
        restRequest.setJsonEntity(JsonObject.mapFrom(iosPhone).toString());
        restClient.performRequest(restRequest);
        Thread.sleep(1000);
    }

    @AfterEach
    public void after() {
        Stream.of(iosPhone, androidPhone)
         .filter(Objects::nonNull)
         .filter(storeItem -> storeItem.getId() != null)
         .forEach(
           storeItem -> {
               DeleteRequest request = DeleteRequest.of(
                 b -> b.index("store-items")
                         .id(storeItem.getId())
               );
               try {
                   elasticsearchClient.delete(request);
               } catch (IOException e) {
                   throw new RuntimeException(e);
               }
           }
         );
    }

    @Test
    void givenRestClient_whenSearchInStoreItemsByName_thenExpectedDocumentsFound() throws Exception {
        indexUsingRestClient();

        Request request = new Request(
          "GET",
          "/store-items/_search");

        JsonObject termJson = new JsonObject().put("name", "IOS smartphone");
        JsonObject matchJson = new JsonObject().put("match", termJson);
        JsonObject queryJson = new JsonObject().put("query", matchJson);
        request.setJsonEntity(queryJson.encode());

        Response response = restClient.performRequest(request);
        String responseBody = EntityUtils.toString(response.getEntity());

        JsonObject json = new JsonObject(responseBody);
        JsonArray hits = json.getJsonObject("hits").getJsonArray("hits");
        List<StoreItem> results = new ArrayList<>(hits.size());

        for (int i = 0; i < hits.size(); i++) {
            JsonObject hit = hits.getJsonObject(i);
            StoreItem fruit = hit.getJsonObject("_source").mapTo(StoreItem.class);
            results.add(fruit);
        }

        assertThat(results)
          .hasSize(1)
          .containsExactlyInAnyOrder(iosPhone);
    }

    @Test
    void givenElasticsearchClient_whenSearchInStoreItemsByName_thenExpectedDocumentsFound() throws Exception {
        indexUsingElasticsearchClient();
        Query query = QueryBuilders.match()
          .field("name")
          .query(FieldValue.of("Android smartphone"))
          .build()
          ._toQuery();

        SearchRequest request = SearchRequest.of(
          b -> b.index("store-items")
                 .query(query)
        );
        SearchResponse<StoreItem> searchResponse = elasticsearchClient
          .search(request, StoreItem.class);

        HitsMetadata<StoreItem> hits = searchResponse.hits();
        List<StoreItem> results = hits.hits().stream()
          .map(Hit::source)
          .collect(Collectors.toList());

        assertThat(results)
          .hasSize(1)
          .containsExactlyInAnyOrder(androidPhone);
    }
}
