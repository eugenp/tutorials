package com.baeldung.dependency.exception.packagenotscanned.app;

import com.baeldung.dependency.exception.packagenotscanned.repository.PackageNotScannedInventoryRepository;
import org.springframework.beans.factory.annotation.Qualifier;
import org.springframework.stereotype.Service;

@Service
public class PackageNotScannedPurchaseDeptService {
    private PackageNotScannedInventoryRepository repository;

    public PackageNotScannedPurchaseDeptService(@Qualifier("dresses") PackageNotScannedInventoryRepository repository) {
        this.repository = repository;
    }
}