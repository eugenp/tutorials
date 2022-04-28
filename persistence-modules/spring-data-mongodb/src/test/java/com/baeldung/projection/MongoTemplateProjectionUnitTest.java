package com.baeldung.projection;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertNotNull;
import static org.junit.jupiter.api.Assertions.assertNull;
import static org.junit.jupiter.api.Assertions.assertTrue;

import java.util.Arrays;
import java.util.List;

import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.mongodb.core.MongoTemplate;
import org.springframework.data.mongodb.core.query.Query;
import org.springframework.test.context.ContextConfiguration;
import org.springframework.test.context.junit.jupiter.SpringExtension;

import com.baeldung.projection.config.ProjectionConfig;
import com.baeldung.projection.model.InStock;
import com.baeldung.projection.model.Inventory;
import com.baeldung.projection.model.Size;

@ExtendWith(SpringExtension.class)
@ContextConfiguration(classes = ProjectionConfig.class)
public class MongoTemplateProjectionUnitTest extends AbstractTestProjection {

    @Autowired
    private MongoTemplate mongoTemplate;

    @BeforeEach
    void setup() {
        List<Inventory> inventoryList = getInventories();

        mongoTemplate.insert(inventoryList, Inventory.class);
    }

    @Test
    void whenIncludeFields_thenOnlyIncludedFieldsAreNotNull() {
        Query query = new Query();
        query.fields()
          .include("item")
          .include("status");

        List<Inventory> inventoryList = mongoTemplate.find(query, Inventory.class);

        assertTrue(inventoryList.size() > 0);

        inventoryList.forEach(i -> {
            assertNotNull(i.getId());
            assertNotNull(i.getItem());
            assertNotNull(i.getStatus());
            assertNull(i.getSize());
            assertNull(i.getInStock());
        });
    }

    @Test
    void whenIncludeFieldsAndExcludeOtherFields_thenOnlyExcludedFieldsAreNull() {
        Query query = new Query();
        query.fields()
          .include("item")
          .include("status")
          .exclude("_id");

        List<Inventory> inventoryList = mongoTemplate.find(query, Inventory.class);

        assertTrue(inventoryList.size() > 0);

        inventoryList.forEach(i -> {
            assertNotNull(i.getItem());
            assertNotNull(i.getStatus());
            assertNull(i.getId());
            assertNull(i.getSize());
            assertNull(i.getInStock());
        });
    }

    @Test
    void whenIncludeAllButExcludeSomeFields_thenOnlyExcludedFieldsAreNull() {
        Query query = new Query();
        query.fields()
          .exclude("status")
          .exclude("inStock");

        List<Inventory> inventoryList = mongoTemplate.find(query, Inventory.class);

        assertTrue(inventoryList.size() > 0);

        inventoryList.forEach(i -> {
            assertNotNull(i.getItem());
            assertNotNull(i.getId());
            assertNotNull(i.getSize());
            assertNull(i.getInStock());
            assertNull(i.getStatus());
        });
    }

    @Test
    void whenIncludeEmbeddedFields_thenEmbeddedFieldsAreNotNull() {
        Query query = new Query();
        query.fields()
          .include("item")
          .include("status")
          .include("size.uom");

        List<Inventory> inventoryList = mongoTemplate.find(query, Inventory.class);

        assertTrue(inventoryList.size() > 0);

        inventoryList.forEach(i -> {
            assertNotNull(i.getItem());
            assertNotNull(i.getStatus());
            assertNotNull(i.getId());
            assertNotNull(i.getSize());
            assertNotNull(i.getSize()
              .getUom());
            assertNull(i.getSize()
              .getHeight());
            assertNull(i.getSize()
              .getWidth());
            assertNull(i.getInStock());
        });
    }

    @Test
    void whenExcludeEmbeddedFields_thenEmbeddedFieldsAreNull() {
        Query query = new Query();
        query.fields()
          .exclude("size.uom");

        List<Inventory> inventoryList = mongoTemplate.find(query, Inventory.class);

        assertTrue(inventoryList.size() > 0);

        inventoryList.forEach(i -> {
            assertNotNull(i.getItem());
            assertNotNull(i.getStatus());
            assertNotNull(i.getId());
            assertNotNull(i.getSize());
            assertNull(i.getSize()
              .getUom());
            assertNotNull(i.getSize()
              .getHeight());
            assertNotNull(i.getSize()
              .getWidth());
            assertNotNull(i.getInStock());
        });
    }

    @Test
    void whenIncludeEmbeddedFieldsInArray_thenEmbeddedFieldsInArrayAreNotNull() {
        Query query = new Query();
        query.fields()
          .include("item")
          .include("status")
          .include("inStock.quantity");

        List<Inventory> inventoryList = mongoTemplate.find(query, Inventory.class);

        assertTrue(inventoryList.size() > 0);

        inventoryList.forEach(i -> {
            assertNotNull(i.getItem());
            assertNotNull(i.getStatus());
            assertNotNull(i.getId());
            assertNotNull(i.getInStock());
            i.getInStock()
              .forEach(stock -> {
                  assertNull(stock.getWareHouse());
                  assertNotNull(stock.getQuantity());
              });
            assertNull(i.getSize());
        });
    }

    @Test
    void whenIncludeEmbeddedFieldsSliceInArray_thenArrayLengthEqualToSlice() {
        Inventory postcard = new Inventory();
        postcard.setItem("postcard");
        postcard.setStatus("A");
        postcard.setSize(new Size(10.0, 15.25, "cm"));

        InStock firstInStock = new InStock("B", 15);
        InStock lastInStock = new InStock("C", 35);

        postcard.setInStock(Arrays.asList(firstInStock, lastInStock));

        mongoTemplate.save(postcard);

        Query query = new Query();
        query.fields()
          .include("item")
          .include("status")
          .slice("inStock", -1);

        List<Inventory> inventoryList = mongoTemplate.find(query, Inventory.class);

        assertTrue(inventoryList.size() > 0);

        inventoryList.forEach(i -> {
            assertNotNull(i.getItem());
            assertNotNull(i.getStatus());
            assertNotNull(i.getId());
            assertNotNull(i.getInStock());
            assertEquals(1, i.getInStock()
              .size());
            assertNull(i.getSize());
        });

        InStock stock = inventoryList.stream()
          .filter(i -> i.getItem()
            .equals("postcard"))
          .map(i -> i.getInStock()
            .get(0))
          .findFirst()
          .orElse(null);

        assertEquals(lastInStock, stock);
    }

}
