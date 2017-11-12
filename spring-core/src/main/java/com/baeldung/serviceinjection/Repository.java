package com.baeldung.serviceinjection;

import java.util.List;

public interface Repository<T> {
    List<T> findAll();
}
