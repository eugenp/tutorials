package com.baeldung.annotations;

import java.io.Serializable;
import java.util.Optional;

import org.springframework.data.repository.CrudRepository;
import org.springframework.data.repository.NoRepositoryBean;

@NoRepositoryBean
public interface MyUtilityRepository<T, ID extends Serializable> extends CrudRepository<T, ID> {

    Optional<T> findById(ID id);

}
