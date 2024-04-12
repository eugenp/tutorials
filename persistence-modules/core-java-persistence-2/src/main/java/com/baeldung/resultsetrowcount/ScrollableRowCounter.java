package com.baeldung.resultsetrowcount;

import java.sql.Connection;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.sql.Statement;

/**
 * A ResultSet counter that uses a scrollable cursor.
 * Scrollable cursors must be supported by the underlying JDBC driver
 * and will not always be available for use.
 */
class ScrollableRowCounter {

    Connection conn;

    ScrollableRowCounter(Connection conn) {
        this.conn = conn;
    }

    int getQueryRowCount(String query) throws SQLException {
        try (Statement statement = conn.createStatement(ResultSet.TYPE_SCROLL_INSENSITIVE, ResultSet.CONCUR_READ_ONLY); ResultSet scrollableRS = statement.executeQuery(query)) {
            scrollableRS.last(); // check if we need a scrollable type for this (probably)
            return scrollableRS.getRow();
            // if we want to process the result set data we can move the cursor back to
            // the beginning using the scrollableRS.beforeFirst() method
        }
    }

}
