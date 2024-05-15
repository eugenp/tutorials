package com.baeldung.readonlytransactions.mysql.dao;

import com.baeldung.readonlytransactions.mysql.spring.repositories.BookRepository;

import java.util.SplittableRandom;

public class MyRepoSpring extends BaseRepo {

    private SplittableRandom random = new SplittableRandom();
    private BookRepository repository;

    public MyRepoSpring(BookRepository repository) {
        this.repository = repository;
    }

    public long runQuery() {
        return execQuery(count -> {
            repository.get(1L + random.nextLong(0, 1000000));
            count.incrementAndGet();
        });
    }
}
