package com.baeldung.throwsexception;

import java.sql.SQLException;
import java.util.List;

public class PersonRepository {

    public List<String> findAll() throws SQLException {
        throw new SQLException();
    }

}
