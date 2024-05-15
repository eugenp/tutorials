package com.baeldung.jersey.exceptionhandling.repo;

import java.util.HashMap;
import java.util.Map;
import java.util.Optional;
import java.util.UUID;

public class Db<T extends Identifiable> {
    private Map<String, T> db = new HashMap<>();

    public Optional<T> findById(String id) {
        return Optional.ofNullable(db.get(id));
    }

    public String save(T t) {
        String id = t.getId();
        if (id == null) {
            id = UUID.randomUUID()
                .toString();
            t.setId(id);
        }
        db.put(id, t);
        return id;
    }

    public void remove(T t) {
        db.entrySet()
            .removeIf(entry -> entry.getValue()
                .getId()
                .equals(t.getId()));
    }
}
