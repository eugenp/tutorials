package com.baeldung.h2db.springboot.daos;

import com.baeldung.h2db.springboot.models.Item;
import org.springframework.data.repository.CrudRepository;

import java.math.BigDecimal;

public interface ItemRepository extends CrudRepository<Item, BigDecimal> {
}
