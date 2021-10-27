package com.baeldung.hexagonal.architecture.repository;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.jdbc.core.namedparam.NamedParameterJdbcTemplate;

public abstract class AbstractRepository {

    @Autowired
    protected NamedParameterJdbcTemplate namedParameterJdbcTemplate;
}
