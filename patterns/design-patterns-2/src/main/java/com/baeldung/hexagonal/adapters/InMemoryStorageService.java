package com.baeldung.hexagonal.adapters;

import com.baeldung.hexagonal.ports.StorageService;

import java.util.HashMap;
import java.util.Map;

public class InMemoryStorageService implements StorageService {

    Map<Integer, String> storageMap = new HashMap<Integer, String>();

    public void store(int id, String data) {
        storageMap.put(id, data);
    }

    public String get(int id) {
        return storageMap.get(id);
    }
}
