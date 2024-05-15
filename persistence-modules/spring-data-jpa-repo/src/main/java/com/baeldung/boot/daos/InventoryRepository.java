package com.baeldung.boot.daos;

import org.springframework.data.repository.CrudRepository;

import com.baeldung.boot.domain.MerchandiseEntity;

public interface InventoryRepository extends CrudRepository<MerchandiseEntity, Long> {
}
