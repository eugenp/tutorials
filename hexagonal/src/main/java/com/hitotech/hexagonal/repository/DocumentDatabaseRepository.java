package com.hitotech.hexagonal.repository;

import org.springframework.stereotype.Repository;

@Repository
public class DocumentDatabaseRepository implements HexagonalRepository {

    @Override
    public String findById(Long id) {
        return "Result from Document Database " + id;
    }

}
