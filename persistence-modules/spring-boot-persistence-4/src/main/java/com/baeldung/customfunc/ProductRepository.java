package com.baeldung.customfunc;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.jpa.repository.query.Procedure;
import org.springframework.data.repository.query.Param;

import java.util.List;

public interface ProductRepository extends JpaRepository<Product, Integer> {

    @Procedure(name = "Product.sha256Hex")
    String getSha256HexByNamedMapping(@Param("value") String value);

    @Query(value = "CALL SHA256_HEX(:value)", nativeQuery = true)
    String getSha256HexByNativeCall(@Param("value") String value);

    @Query(value = "SELECT SHA256_HEX(name) FROM product", nativeQuery = true)
    List<String> getProductNameListInSha256HexByNativeSelect();

    @Query(value = "SELECT sha256Hex(p.name) FROM Product p")
    List<String> getProductNameListInSha256Hex();

}