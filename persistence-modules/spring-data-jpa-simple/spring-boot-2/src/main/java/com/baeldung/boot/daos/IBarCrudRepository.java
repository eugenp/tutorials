package com.baeldung.boot.daos;

import com.baeldung.boot.domain.Bar;
import org.springframework.data.repository.CrudRepository;

import java.io.Serializable;

public interface IBarCrudRepository extends CrudRepository<Bar, Serializable> {
    //
}
