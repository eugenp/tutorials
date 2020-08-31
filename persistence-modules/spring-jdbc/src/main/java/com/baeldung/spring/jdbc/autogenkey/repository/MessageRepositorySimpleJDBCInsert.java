package com.baeldung.spring.jdbc.autogenkey.repository;

import java.util.HashMap;
import java.util.Map;

import javax.sql.DataSource;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.jdbc.core.simple.SimpleJdbcInsert;
import org.springframework.stereotype.Repository;

@Repository
public class MessageRepositorySimpleJDBCInsert {

    SimpleJdbcInsert messageInsert;

    @Autowired
    public MessageRepositorySimpleJDBCInsert(DataSource dataSource) {
        messageInsert = new SimpleJdbcInsert(dataSource).withTableName("sys_message").usingGeneratedKeyColumns("id");
    }

    public long insert(String message) {
        Map<String, Object> parameters = new HashMap<String, Object>(1);
        parameters.put("message", message);
        Number newId = messageInsert.executeAndReturnKey(parameters);
        return (long) newId;
    }

}
