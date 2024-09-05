package com.baledung.spring.jdbc.callprocedures;

import java.util.Map;

import org.springframework.dao.DataAccessException;
import org.springframework.jdbc.core.JdbcTemplate;
import org.springframework.jdbc.object.StoredProcedure;

public class StoredProcedureImpl extends StoredProcedure {

    public StoredProcedureImpl(JdbcTemplate jdbcTemplate, String procName) {
        super(jdbcTemplate, procName);
    }

    private String doSomeProcess(Object procName) {
        //do some processing
        return null;
    }

    @Override
    public Map<String, Object> execute(Map<String, ?> inParams) throws DataAccessException {
        doSomeProcess(inParams);
        return super.execute(inParams);
    }
}
