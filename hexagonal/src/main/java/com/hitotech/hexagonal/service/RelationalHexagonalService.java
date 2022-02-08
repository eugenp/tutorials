package com.hitotech.hexagonal.service;

import com.hitotech.hexagonal.repository.RelationalDatabaseRepository;
import org.springframework.stereotype.Service;

@Service
public class RelationalHexagonalService extends CommonHexagonalService {
    public RelationalHexagonalService(RelationalDatabaseRepository repository) {
        super(repository);
    }
}
