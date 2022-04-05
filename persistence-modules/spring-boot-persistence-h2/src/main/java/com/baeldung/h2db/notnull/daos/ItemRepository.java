package com.baeldung.h2db.notnull.daos;

import com.baeldung.h2db.notnull.models.Item;
import org.springframework.data.repository.CrudRepository;

import java.math.BigDecimal;

public interface ItemRepository extends CrudRepository<Item, BigDecimal> {
}
