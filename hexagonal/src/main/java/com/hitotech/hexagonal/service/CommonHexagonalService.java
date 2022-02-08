package com.hitotech.hexagonal.service;

import com.hitotech.hexagonal.repository.HexagonalRepository;

public abstract class CommonHexagonalService implements HexagonalService {

    private HexagonalRepository repository;

    public CommonHexagonalService(HexagonalRepository repository) {
        this.repository = repository;
    }

    public String getMessage(Long id) {
        return repository.findById(id);
    }
}
