package com.baeldung.jackson.pojomapping;

import com.mongodb.client.MongoDatabase;
import org.bson.UuidRepresentation;
import org.mongojack.JacksonMongoCollection;

public class ProductService {

    private final JacksonMongoCollection<Product> collection;

    public ProductService(MongoDatabase database) {
        this.collection = JacksonMongoCollection.builder()
          .build(database, "products", Product.class, UuidRepresentation.STANDARD);
    }

    public void save(Product product) {
        collection.insertOne(product);
    }

    public Product findById(String id) {
        return collection.findOneById(id);
    }

    public long count() {
        return collection.countDocuments();
    }
}
