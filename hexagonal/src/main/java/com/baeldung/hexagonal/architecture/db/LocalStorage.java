package com.baeldung.hexagonal.architecture.db;

import com.baeldung.hexagonal.architecture.core.domain.Book;
import org.springframework.stereotype.Service;

import java.util.concurrent.ConcurrentHashMap;
import java.util.concurrent.ConcurrentMap;

@Service
public class LocalStorage {

    // IN MEMORY STORAGE
    public final ConcurrentMap<Long, Book> bookDB = new ConcurrentHashMap<>();

}
