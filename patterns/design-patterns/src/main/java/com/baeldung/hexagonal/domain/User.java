package com.baeldung.hexagonal.domain;

import java.util.ArrayList;
import java.util.List;

@SuppressWarnings({ "SpellCheckingInspection", "unused" })
public class User {

    private String id;
    private String name;
    private List<NotificationHandle> handles = new ArrayList<>();

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

    public List<NotificationHandle> getHandles() {
        return handles;
    }

    public void setHandles(List<NotificationHandle> handles) {
        this.handles = handles;
    }
}
