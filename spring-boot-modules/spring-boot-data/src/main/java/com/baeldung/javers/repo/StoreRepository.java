package com.baeldung.javers.repo;

import com.baeldung.javers.domain.Store;
import org.javers.spring.annotation.JaversSpringDataAuditable;
import org.springframework.data.repository.CrudRepository;

@JaversSpringDataAuditable
public interface StoreRepository extends CrudRepository<Store, Integer> {
}
