package com.baeldung.dao.repositories;

import com.baeldung.domain.MerchandiseEntity;
import org.springframework.data.repository.CrudRepository;

public interface InventoryRepository extends CrudRepository<MerchandiseEntity, Long> {
}
