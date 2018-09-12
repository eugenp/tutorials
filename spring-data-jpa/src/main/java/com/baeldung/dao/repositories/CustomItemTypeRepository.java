package com.baeldung.dao.repositories;

import org.springframework.stereotype.Repository;

import com.baeldung.domain.ItemType;

@Repository
public interface CustomItemTypeRepository {

    void deleteCustom(ItemType entity);

    void findThenDelete(Long id);
}
