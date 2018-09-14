package com.baeldung.dependency.exception.componentannotationmissing;

import com.baeldung.dependency.exception.componentannotationmissing.repository.ComponentAnnotationMissingInventoryRepository;
import org.springframework.stereotype.Service;

@Service
public class ComponentAnnotationMissingPurchaseDeptService {
    private ComponentAnnotationMissingInventoryRepository repository;

    public ComponentAnnotationMissingPurchaseDeptService(ComponentAnnotationMissingInventoryRepository repository) {
        this.repository = repository;
    }
}