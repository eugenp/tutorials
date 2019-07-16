package com.baeldung.jnosql.diana.key;

import com.hazelcast.core.Hazelcast;
import org.jnosql.diana.api.Value;
import org.jnosql.diana.api.key.BucketManager;
import org.jnosql.diana.api.key.BucketManagerFactory;
import org.jnosql.diana.api.key.KeyValueConfiguration;
import org.jnosql.diana.api.key.KeyValueEntity;
import org.jnosql.diana.hazelcast.key.HazelcastKeyValueConfiguration;

import java.util.Optional;

public class KeyValueApp {

    private static final String BUCKET_NAME = "books";

    public static void main(String... args) throws Exception {
        KeyValueConfiguration configuration = new HazelcastKeyValueConfiguration();
        try (BucketManagerFactory managerFactory = configuration.get()) {
            BucketManager manager = managerFactory.getBucketManager(BUCKET_NAME);

            Book book = new Book("12345", "JNoSQL in Action", "baeldung", 420);
            KeyValueEntity keyValueEntity = KeyValueEntity.of(book.getIsbn(), book);
            manager.put(keyValueEntity);

            Optional<Value> optionalValue = manager.get("12345");
            Value value = optionalValue.get();
            Book savedBook = value.get(Book.class);
            System.out.println(savedBook);
        }
        Hazelcast.shutdownAll();
    }
}
