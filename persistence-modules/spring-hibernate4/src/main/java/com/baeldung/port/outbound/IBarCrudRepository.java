package com.baeldung.port.outbound;

import java.io.Serializable;

import com.baeldung.port.model.Bar;
import org.springframework.data.repository.CrudRepository;

public interface IBarCrudRepository extends CrudRepository<Bar, Serializable> {
    //
}
