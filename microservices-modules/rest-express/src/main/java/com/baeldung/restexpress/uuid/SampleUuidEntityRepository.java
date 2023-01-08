package com.baeldung.restexpress.uuid;

import com.mongodb.MongoClient;
import com.strategicgains.repoexpress.mongodb.MongodbUuidEntityRepository;

public class SampleUuidEntityRepository
        extends MongodbUuidEntityRepository<SampleUuidEntity> {
    @SuppressWarnings("unchecked")
    public SampleUuidEntityRepository(MongoClient mongo, String dbName) {
        super(mongo, dbName, SampleUuidEntity.class);
    }
}
