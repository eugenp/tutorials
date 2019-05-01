package com.baeldung.hexagonal.repositories;

import com.baeldung.hexagonal.entities.InMemoryUserEntity;

import java.util.Map;
import java.util.concurrent.ConcurrentHashMap;

public class InMemoryUserRepository {

    private Map<String, InMemoryUserEntity> map = new ConcurrentHashMap<>();

    public void save(InMemoryUserEntity entity) {
        map.put(entity.getId(), entity);
    }

    // other operations

}
