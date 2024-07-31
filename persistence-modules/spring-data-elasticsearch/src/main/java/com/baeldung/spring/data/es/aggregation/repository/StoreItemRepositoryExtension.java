package com.baeldung.spring.data.es.aggregation.repository;

import org.springframework.data.domain.Pageable;
import org.springframework.data.elasticsearch.core.SearchPage;

import com.baeldung.spring.data.es.aggregation.model.StoreItem;

public interface StoreItemRepositoryExtension {
    SearchPage<StoreItem> findAllWithAggregations(Pageable pageable);
}
