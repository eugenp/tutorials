package com.baeldung.boot.daos;

import org.springframework.data.repository.CrudRepository;

import com.baeldung.boot.domain.Bar;

import java.io.Serializable;

public interface IBarCrudRepository extends CrudRepository<Bar, Serializable> {
    //
}
