package com.baeldung.couchbase.intro;

import java.util.List;
import java.util.UUID;

import com.couchbase.client.core.CouchbaseException;
import com.couchbase.client.java.Bucket;
import com.couchbase.client.java.Cluster;
import com.couchbase.client.java.CouchbaseCluster;
import com.couchbase.client.java.ReplicaMode;
import com.couchbase.client.java.document.JsonDocument;
import com.couchbase.client.java.document.json.JsonObject;
import com.couchbase.client.java.env.CouchbaseEnvironment;
import com.couchbase.client.java.env.DefaultCouchbaseEnvironment;

public class CodeSnippets {

    static Cluster loadClusterWithDefaultEnvironment() {
        return CouchbaseCluster.create("localhost");
    }

    static Cluster loadClusterWithCustomEnvironment() {
        CouchbaseEnvironment env = DefaultCouchbaseEnvironment.builder().connectTimeout(10000).kvTimeout(3000).build();
        return CouchbaseCluster.create(env, "localhost");
    }

    static Bucket loadDefaultBucketWithBlankPassword(Cluster cluster) {
        return cluster.openBucket();
    }

    static Bucket loadBaeldungBucket(Cluster cluster) {
        return cluster.openBucket("baeldung", "");
    }

    static JsonDocument insertExample(Bucket bucket) {
        JsonObject content = JsonObject.empty().put("name", "John Doe").put("type", "Person").put("email", "john.doe@mydomain.com").put("homeTown", "Chicago");
        String id = UUID.randomUUID().toString();
        JsonDocument document = JsonDocument.create(id, content);
        JsonDocument inserted = bucket.insert(document);
        return inserted;
    }

    static JsonDocument retrieveAndUpsertExample(Bucket bucket, String id) {
        JsonDocument document = bucket.get(id);
        JsonObject content = document.content();
        content.put("homeTown", "Kansas City");
        JsonDocument upserted = bucket.upsert(document);
        return upserted;
    }

    static JsonDocument replaceExample(Bucket bucket, String id) {
        JsonDocument document = bucket.get(id);
        JsonObject content = document.content();
        content.put("homeTown", "Milwaukee");
        JsonDocument replaced = bucket.replace(document);
        return replaced;
    }

    static JsonDocument removeExample(Bucket bucket, String id) {
        JsonDocument removed = bucket.remove(id);
        return removed;
    }

    static JsonDocument getFirstFromReplicaExample(Bucket bucket, String id) {
        try {
            return bucket.get(id);
        } catch (CouchbaseException e) {
            List<JsonDocument> list = bucket.getFromReplica(id, ReplicaMode.FIRST);
            if (!list.isEmpty()) {
                return list.get(0);
            }
        }
        return null;
    }

    static JsonDocument getLatestReplicaVersion(Bucket bucket, String id) {
        long maxCasValue = -1;
        JsonDocument latest = null;
        for (JsonDocument replica : bucket.getFromReplica(id, ReplicaMode.ALL)) {
            if (replica.cas() > maxCasValue) {
                latest = replica;
                maxCasValue = replica.cas();
            }
        }
        return latest;
    }
}
