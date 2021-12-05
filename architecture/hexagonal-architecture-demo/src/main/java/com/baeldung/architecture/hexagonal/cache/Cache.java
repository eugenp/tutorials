package com.baeldung.architecture.hexagonal.cache;

import java.util.Optional;

public interface Cache {
    void put(String key, String value);

    Optional<String> get(String key);
}
