package com.hitotech.hexagonal.repository;

import org.springframework.stereotype.Repository;

@Repository
public class RelationalDatabaseRepository implements HexagonalRepository {

    @Override
    public String findById(Long id) {
        return "Result from Relational Database " + id;
    }

}
