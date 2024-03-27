package com.baeldung.spring.data.jpa.queryjsonb;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.JpaSpecificationExecutor;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;

import java.util.List;

public interface ProductRepository extends JpaRepository<Product, Long>, JpaSpecificationExecutor<Product> {
    @Query(value = "SELECT * FROM product WHERE attributes ->> ?1 = ?2", nativeQuery = true)
    List<Product> findByAttribute(String key, String value);

    @Query(value = "SELECT * FROM product WHERE attributes -> ?1 ->> ?2 = ?3", nativeQuery = true)
    List<Product> findByNestedAttribute(String key1, String key2,  String value);

    @Query(value = "SELECT * FROM product WHERE jsonb_extract_path_text(attributes, ?1) = ?2", nativeQuery = true)
    List<Product> findByJsonPath(String path, String value);

    @Query(value = "SELECT * FROM product WHERE jsonb_extract_path_text(attributes, ?1, ?2) = ?3", nativeQuery = true)
    List<Product> findByNestedJsonPath(String key1, String key2, String value);


}