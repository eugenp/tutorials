package com.baeldung.spring.data.es.aggregation.repository;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.Pageable;
import org.springframework.data.elasticsearch.client.elc.NativeQuery;
import org.springframework.data.elasticsearch.core.ElasticsearchOperations;
import org.springframework.data.elasticsearch.core.SearchHitSupport;
import org.springframework.data.elasticsearch.core.SearchHits;
import org.springframework.data.elasticsearch.core.SearchPage;
import org.springframework.data.elasticsearch.core.query.Query;
import org.springframework.stereotype.Component;

import com.baeldung.spring.data.es.aggregation.model.StoreItem;

import co.elastic.clients.elasticsearch._types.aggregations.Aggregation;

@Component
public class StoreItemRepositoryExtensionImpl implements StoreItemRepositoryExtension {

    @Autowired
    private ElasticsearchOperations elasticsearchOperations;

    @Override
    public SearchPage<StoreItem> findAllWithAggregations(Pageable pageable) {
        Query query = NativeQuery.builder()
          .withAggregation("type_aggregation",
            Aggregation.of(b -> b.terms(t -> t.field("type"))))
          .build();
        SearchHits<StoreItem> response = elasticsearchOperations.search(query, StoreItem.class);
        return SearchHitSupport.searchPageFor(response, pageable);
    }
}
