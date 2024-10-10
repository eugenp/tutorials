package com.baeldung.storefileblob;

import static org.junit.jupiter.api.Assertions.assertArrayEquals;
import static org.junit.jupiter.api.Assertions.assertTrue;

import java.io.IOException;
import java.sql.SQLException;

import org.junit.Test;
import org.junit.jupiter.api.BeforeAll;
import org.junit.jupiter.params.ParameterizedTest;
import org.junit.jupiter.params.provider.CsvSource;

public class JdbcConnectionUnitTest {

    private static final String TEST_FILE_PATH = "warehouse.png";
    private static final String RETRIEVED_FILE_PATH = "retrieve-warehouse.png";
    private static JdbcConnection jdbcConnection;
    String selectBlob = """
        SELECT picture FROM warehouses WHERE id=?
        """;

    @BeforeAll
    public static void setUp() throws SQLException {
        jdbcConnection = new JdbcConnection();
        jdbcConnection.createSchema();
    }

    @Test
    public void givenDatabaseSchema_whenJdbcConnectionIsEstablished_thenCreateSchema() throws SQLException {
        assertTrue(jdbcConnection.createSchema());
    }

    @ParameterizedTest
    @CsvSource({ "1, 'Liu', 3000", "2, 'Walmart', 5000" })
    public void givenBlobFile_whenInsertingTheBlobFileAsByteArray_thenSuccessful(int id, String name, int capacity) throws SQLException, IOException {
        boolean result = jdbcConnection.insertFile(id, name, capacity, TEST_FILE_PATH);
        assertTrue(result);
    }

    @ParameterizedTest
    @CsvSource({ "7, 'Dylan', 8000", "8, 'Jumia', 1000" })
    public void givenBlobFile_whenInsertingTheFileIntoTheDatabaseInChunks_thenSuccessful(int id, String name, int capacity) throws SQLException, IOException {
        boolean result = jdbcConnection.insertFileAsStream(id, name, capacity, TEST_FILE_PATH);
        assertTrue(result);
    }

    @Test
    public void givenAnEntityWithBlob_whenReadingBlobAndConvertingItToFile_thenSuccessful() throws SQLException, IOException {
        boolean result = JdbcConnection.writeBlobToFile(selectBlob, 1, 2, RETRIEVED_FILE_PATH);
        byte[] expectedBytes = JdbcConnection.convertFileToByteArray(TEST_FILE_PATH);
        byte[] retrievedBytes = JdbcConnection.convertFileToByteArray(RETRIEVED_FILE_PATH);
        assertArrayEquals(expectedBytes, retrievedBytes);
    }

}