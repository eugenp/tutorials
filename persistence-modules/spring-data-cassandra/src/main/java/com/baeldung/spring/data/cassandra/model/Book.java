package com.baeldung.spring.data.cassandra.model;

import java.util.HashSet;
import java.util.Set;
import java.util.UUID;

import org.springframework.cassandra.core.Ordering;
import org.springframework.cassandra.core.PrimaryKeyType;
import org.springframework.data.cassandra.mapping.Column;
import org.springframework.data.cassandra.mapping.PrimaryKeyColumn;
import org.springframework.data.cassandra.mapping.Table;

@Table
public class Book {

    @PrimaryKeyColumn(name = "id", ordinal = 0, type = PrimaryKeyType.CLUSTERED, ordering = Ordering.DESCENDING)
    private UUID id;

    @PrimaryKeyColumn(name = "title", ordinal = 1, type = PrimaryKeyType.PARTITIONED)
    private String title;

    @PrimaryKeyColumn(name = "publisher", ordinal = 2, type = PrimaryKeyType.PARTITIONED)
    private String publisher;

    @Column
    private Set<String> tags = new HashSet<>();

    public Book(final UUID id, final String title, final String publisher, final Set<String> tags) {
        this.id = id;
        this.title = title;
        this.publisher = publisher;
        this.tags.addAll(tags);
    }

    public UUID getId() {
        return id;
    }

    public String getTitle() {
        return title;
    }

    public String getPublisher() {
        return publisher;
    }

    public Set getTags() {
        return tags;
    }

    public void setId(final UUID id) {
        this.id = id;
    }

    public void setTitle(final String title) {
        this.title = title;
    }

    public void setPublisher(final String publisher) {
        this.publisher = publisher;
    }

    public void setTags(final Set<String> tags) {
        this.tags = tags;
    }

}
