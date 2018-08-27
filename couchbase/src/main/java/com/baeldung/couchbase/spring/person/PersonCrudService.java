package com.baeldung.couchbase.spring.person;

import java.util.List;
import java.util.UUID;

import javax.annotation.PostConstruct;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import com.baeldung.couchbase.spring.service.CrudService;
import com.baeldung.couchbase.spring.service.TutorialBucketService;
import com.couchbase.client.java.Bucket;
import com.couchbase.client.java.ReplicaMode;
import com.couchbase.client.java.document.JsonDocument;

@Service
public class PersonCrudService implements CrudService<Person> {

    @Autowired
    private TutorialBucketService bucketService;

    @Autowired
    private PersonDocumentConverter converter;

    private Bucket bucket;

    @PostConstruct
    private void init() {
        bucket = bucketService.getBucket();
    }

    @Override
    public void create(Person person) {
        if (person.getId() == null) {
            person.setId(UUID.randomUUID().toString());
        }
        JsonDocument document = converter.toDocument(person);
        bucket.insert(document);
    }

    @Override
    public Person read(String id) {
        JsonDocument doc = bucket.get(id);
        return (doc != null ? converter.fromDocument(doc) : null);
    }

    @Override
    public Person readFromReplica(String id) {
        List<JsonDocument> docs = bucket.getFromReplica(id, ReplicaMode.FIRST);
        return (docs.isEmpty() ? null : converter.fromDocument(docs.get(0)));
    }

    @Override
    public void update(Person person) {
        JsonDocument document = converter.toDocument(person);
        bucket.upsert(document);
    }

    @Override
    public void delete(String id) {
        bucket.remove(id);
    }

    @Override
    public boolean exists(String id) {
        return bucket.exists(id);
    }
}
