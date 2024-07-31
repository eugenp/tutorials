package com.baeldung.connectionstatus;

import org.junit.jupiter.api.Test;

import java.sql.Connection;

import static org.junit.jupiter.api.Assertions.assertFalse;
import static org.junit.jupiter.api.Assertions.assertNotNull;
import static org.junit.jupiter.api.Assertions.assertTrue;

class ConnectionValidationUnitTest
{
    @Test
    void givenConnectionObject_whenCreated_thenIsNotClosed()
            throws Exception
    {
        Connection connection = ConnectionValidation.getConnection();
        assertNotNull(connection);
        assertFalse(connection.isClosed());
    }

    @Test
    void givenConnectionObject_whenCreated_thenIsValid()
            throws Exception
    {
        Connection connection = ConnectionValidation.getConnection();
        assertTrue(connection.isValid(0));
    }

    @Test
    void givenConnectionObject_whenValidated_thenIsValid()
            throws Exception
    {
        Connection connection = ConnectionValidation.getConnection();
        assertTrue(ConnectionValidation.isConnectionValid(connection));
    }

}
