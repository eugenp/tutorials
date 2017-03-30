package com.baeldung.persistence.dao;

import java.io.Serializable;

import com.baeldung.persistence.model.Bar;
import org.springframework.data.repository.CrudRepository;

public interface IBarCrudRepository extends CrudRepository<Bar, Serializable> {
    //
}
