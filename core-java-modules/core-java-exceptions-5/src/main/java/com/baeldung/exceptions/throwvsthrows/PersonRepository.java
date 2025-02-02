package com.baeldung.exceptions.throwvsthrows;

import java.sql.SQLException;
import java.util.List;

public class PersonRepository {

    public List<String> findAll() throws SQLException {
        throw new SQLException();
    }

}
