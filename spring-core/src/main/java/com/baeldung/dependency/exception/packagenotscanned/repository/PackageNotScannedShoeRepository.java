package com.baeldung.dependency.exception.packagenotscanned.repository;

import org.springframework.beans.factory.annotation.Qualifier;
import org.springframework.stereotype.Repository;

@Qualifier("shoes")
@Repository
public class PackageNotScannedShoeRepository implements PackageNotScannedInventoryRepository {
}
