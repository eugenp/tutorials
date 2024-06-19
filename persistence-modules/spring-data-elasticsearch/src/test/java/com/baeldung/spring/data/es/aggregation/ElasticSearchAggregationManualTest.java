package com.baeldung.spring.data.es.aggregation;

import java.util.List;
import java.util.Map;
import java.util.stream.Collectors;

import org.assertj.core.api.Assertions;
import org.junit.jupiter.api.AfterEach;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.Pageable;
import org.springframework.data.elasticsearch.client.elc.ElasticsearchAggregations;
import org.springframework.data.elasticsearch.core.SearchHit;
import org.springframework.data.elasticsearch.core.SearchHits;
import org.springframework.test.context.ContextConfiguration;
import org.springframework.test.context.junit.jupiter.SpringExtension;

import com.baeldung.spring.data.es.aggregation.config.ElasticSearchConfig;
import com.baeldung.spring.data.es.aggregation.model.StoreItem;
import com.baeldung.spring.data.es.aggregation.repository.StoreItemRepository;

import co.elastic.clients.elasticsearch._types.aggregations.MultiBucketBase;

/**
 * This Manual test requires: Elasticsearch instance running on localhost:9200.
 * 
 * The following docker command can be used: docker run -d --name es71711 -p 9200:9200 -e "discovery.type=single-node" elasticsearch:7.17.11
 */
@ExtendWith(SpringExtension.class)
@ContextConfiguration(classes = ElasticSearchConfig.class)
public class ElasticSearchAggregationManualTest {
    private static final List<StoreItem>  EXPECTED_ITEMS = List.of(
      new StoreItem("Multimedia", "PC Monitor", 1000L),
      new StoreItem("Multimedia", "Headphones", 200L),
      new StoreItem("Home tech", "Barbecue Grill", 2000L),
      new StoreItem("Pets", "Dog Toy", 10L),
      new StoreItem("Pets", "Cat shampoo", 5L));

    @Autowired
    private StoreItemRepository repository;

    @BeforeEach
    public void before() {
        repository.saveAll(EXPECTED_ITEMS);
    }

    @AfterEach
    public void after() {
        repository.deleteAll();
    }

    @Test
    void givenFullTitle_whenRunMatchQuery_thenDocIsFound() {
        SearchHits<StoreItem> searchHits = repository.findAllWithAggregations(Pageable.ofSize(2))
          .getSearchHits();
        List<StoreItem> data = searchHits.getSearchHits()
          .stream()
          .map(SearchHit::getContent)
          .toList();

        Assertions.assertThat(data).containsAll(EXPECTED_ITEMS);

        Map<String, Long> aggregatedData = ((ElasticsearchAggregations) searchHits
          .getAggregations())
          .get("type_aggregation")
          .aggregation()
          .getAggregate()
          .sterms()
          .buckets()
          .array()
          .stream()
          .collect(Collectors.toMap(bucket -> bucket.key()
            .stringValue(), MultiBucketBase::docCount));

        Assertions.assertThat(aggregatedData).containsExactlyInAnyOrderEntriesOf(
          Map.of("Multimedia", 2L, "Home tech", 1L, "Pets", 2L));
    }
}
