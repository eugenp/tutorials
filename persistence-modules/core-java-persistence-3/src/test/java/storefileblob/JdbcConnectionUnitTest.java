package storefileblob;

import org.junit.Test;
import org.junit.jupiter.api.MethodOrderer;
import org.junit.jupiter.api.Order;
import org.junit.jupiter.api.TestMethodOrder;

import java.io.IOException;
import java.sql.SQLException;

import static org.junit.jupiter.api.Assertions.assertTrue;

@TestMethodOrder(MethodOrderer.OrderAnnotation.class)
public class JdbcConnectionUnitTest {

    String selectBlob = """
        SELECT picture FROM warehouses WHERE id=?
        """;

    @Test
    @Order(1)
    public void givenDatabaseSchema_whenJdbcConnectionIsEstablished_thenCreateSchema() throws SQLException {
        JdbcConnection jdbcConnection = new JdbcConnection();
        assertTrue(jdbcConnection.createSchema());
    }

    @Test
    @Order(3)
    public void givenBlobFile_whenInsertingTheBlobFileIntoTheWarehouseTable_thenSuccessful() throws SQLException, IOException {
        JdbcConnection jdbcConnection = new JdbcConnection();
        jdbcConnection.createSchema();
        assertTrue(jdbcConnection.insertFile(4, "Liu", 3000, "warehouse.png"));
    }

    @Test
    @Order(2)
    public void givenBlobFile_whenInsertingTheFileIntoTheDatabaseAsStream_thenSuccessful() throws SQLException, IOException {
        JdbcConnection jdbcConnection = new JdbcConnection();
        jdbcConnection.createSchema();
        assertTrue(jdbcConnection.insertFileAsStream(6, "Liu", 3000, "warehouse.png"));
    }

    @Test
    @Order(4)
    public void givenAnEntityWithBlob_whenReadingBlobAndConvertingItToFile_thenSuccessful() throws SQLException, IOException {
        JdbcConnection jdbcConnection = new JdbcConnection();
        assertTrue(JdbcConnection.writeBlobToFile(selectBlob, 1, 6, "retrieve-warehouse.png"));
    }

}