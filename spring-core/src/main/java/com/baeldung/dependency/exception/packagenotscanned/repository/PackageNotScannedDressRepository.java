package com.baeldung.dependency.exception.packagenotscanned.repository;

import org.springframework.beans.factory.annotation.Qualifier;
import org.springframework.stereotype.Repository;

@Qualifier("dresses")
@Repository
public class PackageNotScannedDressRepository implements PackageNotScannedInventoryRepository {
}
