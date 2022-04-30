package com.baeldung.resultsetrowcount;

import org.junit.jupiter.api.AfterEach;
import org.junit.jupiter.api.Assertions;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;

import java.sql.Connection;
import java.sql.SQLException;

class ScrollableRowCounterUnitTest {

    Connection conn;

    @BeforeEach
    void setUp() throws SQLException {
        conn = RowCounterApp.createDummyDB();
    }

    @AfterEach
    void tearDown() throws SQLException {
        conn.close();
    }

    @Test
    public void givenDummyDBWithThreeRows_whenAskingForScrollableRowCountForSelectStar_thenCountIsThree() throws SQLException {
        ScrollableRowCounter counter = new ScrollableRowCounter(conn);

        int queryRowCount = counter.getQueryRowCount("SELECT * FROM STORAGE");

        Assertions.assertEquals(3, queryRowCount);
    }

    @Test
    public void givenDummyDBWithThreeRows_whenAskingForScrollableRowCountForSelect1and2_thenCountIsTwo() throws SQLException {
        ScrollableRowCounter counter = new ScrollableRowCounter(conn);

        int queryRowCount = counter.getQueryRowCount("SELECT * FROM STORAGE WHERE ID IN (1,2)");

        Assertions.assertEquals(2, queryRowCount);
    }

}
