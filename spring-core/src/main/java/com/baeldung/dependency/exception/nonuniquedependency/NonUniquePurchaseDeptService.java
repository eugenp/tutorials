package com.baeldung.dependency.exception.nonuniquedependency;

import com.baeldung.dependency.exception.nonuniquedependency.repository.NonUniqueInventoryRepository;
import org.springframework.stereotype.Service;

@Service
public class NonUniquePurchaseDeptService {
    private NonUniqueInventoryRepository repository;

    public NonUniquePurchaseDeptService(NonUniqueInventoryRepository repository) {
        this.repository = repository;
    }
}