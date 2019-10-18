package com.baeldung.dependency.exception.repository;

import org.springframework.beans.factory.annotation.Qualifier;
import org.springframework.context.annotation.Primary;
import org.springframework.stereotype.Repository;

@Qualifier("dresses")
@Repository
public class DressRepository implements InventoryRepository {
}
