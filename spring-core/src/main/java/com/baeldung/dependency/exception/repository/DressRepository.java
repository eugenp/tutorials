package com.baeldung.dependency.exception.repository;

import org.springframework.context.annotation.Primary;
import org.springframework.stereotype.Repository;

@Primary
@Repository
public class DressRepository implements InventoryRepository {
}
