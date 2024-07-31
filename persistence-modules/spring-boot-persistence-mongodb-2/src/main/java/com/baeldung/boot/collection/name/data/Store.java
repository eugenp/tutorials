package com.baeldung.boot.collection.name.data;

import org.springframework.data.annotation.Id;
import org.springframework.data.mongodb.core.mapping.Document;

@Document("store-#{@environment.getProperty('collection.suffix')}")
public class Store {
    @Id
    private String id;

    private String name;

    public Store() {
    }

    public Store(String name) {
        super();
        this.name = name;
    }

    public String getId() {
        return id;
    }

    public void setId(String id) {
        this.id = id;
    }

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }
}
