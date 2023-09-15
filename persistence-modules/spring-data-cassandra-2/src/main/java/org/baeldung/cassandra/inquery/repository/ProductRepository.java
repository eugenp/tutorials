package org.baeldung.cassandra.inquery.repository;

import org.baeldung.cassandra.inquery.model.Product;
import org.springframework.data.cassandra.repository.CassandraRepository;
import org.springframework.data.cassandra.repository.Query;
import org.springframework.data.repository.query.Param;
import org.springframework.stereotype.Repository;

import java.util.List;
import java.util.UUID;

@Repository
public interface ProductRepository extends CassandraRepository<Product, UUID> {

    @Query("select * from product where product_id in :productIds")
    List<Product> findByProductIds(@Param("productIds") List<UUID> productIds);

    @Query("select * from product where product_id = :productId and product_name in :productNames")
    List<Product> findByProductIdAndNames(@Param("productId") UUID productId, @Param("productNames") List<String> productNames);
}
