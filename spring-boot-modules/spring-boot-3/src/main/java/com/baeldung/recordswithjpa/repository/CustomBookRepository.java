package com.baeldung.recordswithjpa.repository;

import com.baeldung.recordswithjpa.records.CustomBookRecord;

import java.util.List;

public interface CustomBookRepository {
    List<CustomBookRecord> findAllBooks();
}
