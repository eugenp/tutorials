package com.baeldung.projection.repository;

import java.util.List;

import org.springframework.data.mongodb.repository.MongoRepository;
import org.springframework.data.mongodb.repository.Query;

import com.baeldung.projection.model.Inventory;

public interface InventoryRepository extends MongoRepository<Inventory, String> {

    @Query(value = "{ 'status' : ?0 }", fields = "{ 'item' : 1, 'status' : 1 }")
    List<Inventory> findByStatusIncludeItemAndStatusFields(String status);

    @Query(value = "{ 'status' : ?0 }", fields = "{ 'item' : 1, 'status' : 1, '_id' : 0 }")
    List<Inventory> findByStatusIncludeItemAndStatusExcludeIdFields(String status);

    @Query(value = "{ 'status' : ?0 }", fields = "{ 'status' : 0, 'inStock' : 0 }")
    List<Inventory> findByStatusIncludeAllButStatusAndStockFields(String status);

    @Query(value = "{ 'status' : ?0 }", fields = "{ 'item' : 1, 'status' : 1, 'size.uom': 1 }")
    List<Inventory> findByStatusIncludeEmbeddedFields(String status);

    @Query(value = "{ 'status' : ?0 }", fields = "{ 'size.uom': 0 }")
    List<Inventory> findByStatusExcludeEmbeddedFields(String status);

    @Query(value = "{ 'status' : ?0 }", fields = "{ 'item' : 1, 'status' : 1, 'inStock.quantity': 1 }")
    List<Inventory> findByStatusIncludeEmbeddedFieldsInArray(String status);

    @Query(value = "{ 'status' : ?0 }", fields = "{ 'item' : 1, 'status' : 1, 'inStock': { $slice: -1 } }")
    List<Inventory> findByStatusIncludeEmbeddedFieldsLastElementInArray(String status);

}