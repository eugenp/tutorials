package org.baeldung.spring.data.cassandra.model;

import org.springframework.cassandra.core.Ordering;
import org.springframework.cassandra.core.PrimaryKeyType;
import org.springframework.data.cassandra.mapping.Column;
import org.springframework.data.cassandra.mapping.PrimaryKeyColumn;
import org.springframework.data.cassandra.mapping.Table;

import java.util.HashSet;
import java.util.Set;
import java.util.UUID;

@Table
public class Event {
    @PrimaryKeyColumn(name = "id", ordinal = 2, type = PrimaryKeyType.CLUSTERED, ordering = Ordering.DESCENDING)
    private UUID id;
    @PrimaryKeyColumn(name = "type", ordinal = 0, type = PrimaryKeyType.PARTITIONED)
    private String type;
    @PrimaryKeyColumn(name = "bucket", ordinal = 1, type = PrimaryKeyType.PARTITIONED)
    private String bucket;
    @Column
    private Set tags = new HashSet();

    public Event(UUID id, String type, String bucket, Set tags) {
        this.id = id;
        this.type = type;
        this.bucket = bucket;
        this.tags.addAll(tags);
    }

    public UUID getId() {
        return id;
    }

    public String getType() {
        return type;
    }

    public String getBucket() {
        return bucket;
    }

    public Set getTags() {
        return tags;
    }
}
