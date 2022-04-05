package com.baeldung.boot.daos;

import org.springframework.stereotype.Repository;

import com.baeldung.boot.domain.ItemType;

@Repository
public interface CustomItemTypeRepository {

    void deleteCustom(ItemType entity);

    void findThenDelete(Long id);
}
