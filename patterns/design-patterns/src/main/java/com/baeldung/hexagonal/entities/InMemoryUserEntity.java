package com.baeldung.hexagonal.entities;

import java.util.ArrayList;
import java.util.List;
import java.util.Map;

@SuppressWarnings({ "unused", "SpellCheckingInspection" })
public class InMemoryUserEntity {

    private String id;
    private String name;
    private List<Map<String, String>> handles = new ArrayList<>();

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

    public List<Map<String, String>> getHandles() {
        return handles;
    }

    public void setHandles(List<Map<String, String>> handles) {
        this.handles = handles;
    }

}
