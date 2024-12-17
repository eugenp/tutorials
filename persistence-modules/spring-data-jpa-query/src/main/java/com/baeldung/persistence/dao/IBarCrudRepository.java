package com.baeldung.persistence.dao;

import java.io.Serializable;

import org.springframework.data.repository.CrudRepository;

import com.baeldung.persistence.model.Bar;

public interface IBarCrudRepository extends CrudRepository<Bar, Serializable> {
    //
}
