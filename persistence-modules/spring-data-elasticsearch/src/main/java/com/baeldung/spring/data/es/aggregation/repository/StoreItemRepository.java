package com.baeldung.spring.data.es.aggregation.repository;

import org.springframework.data.elasticsearch.repository.ElasticsearchRepository;
import org.springframework.stereotype.Repository;

import com.baeldung.spring.data.es.aggregation.model.StoreItem;

@Repository
public interface StoreItemRepository extends ElasticsearchRepository<StoreItem, String>,
  StoreItemRepositoryExtension {
}
