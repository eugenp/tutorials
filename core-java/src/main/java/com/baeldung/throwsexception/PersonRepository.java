package com.baeldung.throwsexception;

import javax.annotation.Nullable;
import java.sql.SQLException;
import java.util.List;

public class PersonRepository {

    @Nullable
    public String findNameById(String id) {
        return id == null ? null : "Name";
    }

    public List<String> findAll() throws SQLException {
        throw new SQLException();
    }

}
