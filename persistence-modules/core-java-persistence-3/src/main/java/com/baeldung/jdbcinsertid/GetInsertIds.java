package com.baeldung.jdbcinsertid;

import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.sql.Statement;
import java.util.ArrayList;
import java.util.Random;
import java.util.UUID;
import java.util.List;

public class GetInsertIds {
    public List<Long> insertAndReturnIds(Connection connection) throws SQLException {
        String sql = "INSERT INTO employees (first_name, last_name, salary) VALUES (?, ?, ?)";
        PreparedStatement statement = connection.prepareStatement(sql, Statement.RETURN_GENERATED_KEYS);
        statement.setString(1, "first");
        statement.setString(2, "last");
        statement.setDouble(3, 100.0);
        int numRows = statement.executeUpdate();

        ResultSet generatedKeys = statement.getGeneratedKeys();
        List<Long> insertIds = new ArrayList<>();
        while (generatedKeys.next()) {
            insertIds.add(generatedKeys.getLong(1));
        }

        return insertIds;
    }
}
