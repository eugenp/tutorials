package com.baeldung.hexagonal.ports;

public interface StorageService {
    void store(int id, String data);

    String get(int id);
}
