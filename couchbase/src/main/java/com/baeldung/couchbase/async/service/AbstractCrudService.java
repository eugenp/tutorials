package com.baeldung.couchbase.async.service;

import java.util.ArrayList;
import java.util.List;
import java.util.UUID;
import java.util.concurrent.TimeUnit;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import com.baeldung.couchbase.async.CouchbaseEntity;
import com.couchbase.client.core.BackpressureException;
import com.couchbase.client.core.time.Delay;
import com.couchbase.client.java.AsyncBucket;
import com.couchbase.client.java.Bucket;
import com.couchbase.client.java.ReplicaMode;
import com.couchbase.client.java.document.JsonDocument;
import com.couchbase.client.java.util.retry.RetryBuilder;

import rx.Observable;
import rx.functions.Action1;
import rx.functions.Func1;

public abstract class AbstractCrudService<T extends CouchbaseEntity> implements CrudService<T> {

    private static final Logger logger = LoggerFactory.getLogger(AbstractCrudService.class);

    private BucketService bucketService;
    private Bucket bucket;
    private JsonDocumentConverter<T> converter;

    public AbstractCrudService(BucketService bucketService, JsonDocumentConverter<T> converter) {
        this.bucketService = bucketService;
        this.converter = converter;
    }

    protected void loadBucket() {
        bucket = bucketService.getBucket();
    }

    @Override
    public void create(T t) {
        if (t.getId() == null) {
            t.setId(UUID.randomUUID().toString());
        }
        JsonDocument doc = converter.toDocument(t);
        bucket.insert(doc);
    }

    @Override
    public T read(String id) {
        JsonDocument doc = bucket.get(id);
        return (doc == null ? null : converter.fromDocument(doc));
    }

    @Override
    public T readFromReplica(String id) {
        List<JsonDocument> docs = bucket.getFromReplica(id, ReplicaMode.FIRST);
        return (docs.isEmpty() ? null : converter.fromDocument(docs.get(0)));
    }

    @Override
    public void update(T t) {
        JsonDocument doc = converter.toDocument(t);
        bucket.upsert(doc);
    }

    @Override
    public void delete(String id) {
        bucket.remove(id);
    }

    @Override
    public List<T> readBulk(Iterable<String> ids) {
        final AsyncBucket asyncBucket = bucket.async();
        Observable<JsonDocument> asyncOperation = Observable.from(ids).flatMap(new Func1<String, Observable<JsonDocument>>() {
            public Observable<JsonDocument> call(String key) {
                return asyncBucket.get(key);
            }
        });

        final List<T> items = new ArrayList<T>();
        try {
            asyncOperation.toBlocking().forEach(new Action1<JsonDocument>() {
                public void call(JsonDocument doc) {
                    T item = converter.fromDocument(doc);
                    items.add(item);
                }
            });
        } catch (Exception e) {
            logger.error("Error during bulk get", e);
        }

        return items;
    }

    @Override
    public void createBulk(Iterable<T> items) {
        final AsyncBucket asyncBucket = bucket.async();
        Observable.from(items).flatMap(new Func1<T, Observable<JsonDocument>>() {
            @SuppressWarnings("unchecked")
            @Override
            public Observable<JsonDocument> call(final T t) {
                if (t.getId() == null) {
                    t.setId(UUID.randomUUID().toString());
                }
                JsonDocument doc = converter.toDocument(t);
                return asyncBucket.insert(doc).retryWhen(RetryBuilder.anyOf(BackpressureException.class).delay(Delay.exponential(TimeUnit.MILLISECONDS, 100)).max(10).build());
            }
        }).last().toBlocking().single();
    }

    @Override
    public void updateBulk(Iterable<T> items) {
        final AsyncBucket asyncBucket = bucket.async();
        Observable.from(items).flatMap(new Func1<T, Observable<JsonDocument>>() {
            @SuppressWarnings("unchecked")
            @Override
            public Observable<JsonDocument> call(final T t) {
                JsonDocument doc = converter.toDocument(t);
                return asyncBucket.upsert(doc).retryWhen(RetryBuilder.anyOf(BackpressureException.class).delay(Delay.exponential(TimeUnit.MILLISECONDS, 100)).max(10).build());
            }
        }).last().toBlocking().single();
    }

    @Override
    public void deleteBulk(Iterable<String> ids) {
        final AsyncBucket asyncBucket = bucket.async();
        Observable.from(ids).flatMap(new Func1<String, Observable<JsonDocument>>() {
            @SuppressWarnings("unchecked")
            @Override
            public Observable<JsonDocument> call(String key) {
                return asyncBucket.remove(key).retryWhen(RetryBuilder.anyOf(BackpressureException.class).delay(Delay.exponential(TimeUnit.MILLISECONDS, 100)).max(10).build());
            }
        }).last().toBlocking().single();
    }

    @Override
    public boolean exists(String id) {
        return bucket.exists(id);
    }
}
