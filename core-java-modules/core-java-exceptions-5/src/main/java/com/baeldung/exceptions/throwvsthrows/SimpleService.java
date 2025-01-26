package com.baeldung.exceptions.throwvsthrows;

import java.sql.SQLException;

public class SimpleService {

    private PersonRepository personRepository = new PersonRepository();

    public void wrappingException() {
        try {
            personRepository.findAll();
        } catch (SQLException e) {
            throw new DataAccessException("SQL Exception", e);
        }
    }

    public void runtimeNullPointerException() {
        String a = null;
        a.length();
    }

}
