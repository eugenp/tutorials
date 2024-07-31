package com.baeldung.dependency.exception.repository;

import org.springframework.beans.factory.annotation.Qualifier;
import org.springframework.stereotype.Repository;

@Qualifier("shoes")
@Repository
public class ShoeRepository implements InventoryRepository {
}
