package com.baeldung.resultsetrowcount;

import java.sql.Connection;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.sql.Statement;

/**
 * A ResultSet counter that iterates through the query
 * results and increments a counter variable until it
 * reaches the last result.
 *
 * This solution will work on all cases but is ineffective,
 * especially if the ResultSet will be discarded afterwards.
 */
class StandardRowCounter {
    Connection conn;

    StandardRowCounter(Connection conn) {
        this.conn = conn;
    }

    int getQueryRowCount(String query) throws SQLException {
        try (Statement statement = conn.createStatement(); ResultSet standardRS = statement.executeQuery(query)) {
            int size = 0;
            while (standardRS.next()) {
                size++;
            }
            return size;
        }
    }
}
