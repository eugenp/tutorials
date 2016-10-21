package com.baeldung.spring.data.es.repositories;

import com.baeldung.spring.data.es.model.ProductInfo;
import org.socialsignin.spring.data.dynamodb.repository.EnableScan;
import org.springframework.data.repository.CrudRepository;

import java.util.List;

@EnableScan
public interface ProductInfoRepository extends CrudRepository<ProductInfo, String> {
    List<ProductInfo> findById(String id);
}
