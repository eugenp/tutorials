package com.stackify.test;

import java.sql.Connection;

import org.junit.jupiter.api.Test;

import com.stackify.utils.ConnectionUtil;

import static org.junit.jupiter.api.Assertions.*;

public interface DatabaseConnectionTest {

    @Test
    default void testDatabaseConnection() {
        Connection con = ConnectionUtil.getConnection();
        assertNotNull(con);
    }

}
