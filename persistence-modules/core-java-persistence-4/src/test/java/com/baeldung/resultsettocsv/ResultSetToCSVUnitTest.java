package com.baeldung.resultsettocsv;

import static org.assertj.core.api.Assertions.assertThat;

import java.sql.PreparedStatement;
import java.sql.SQLException;
import java.util.List;

import org.junit.jupiter.api.AfterEach;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;

import com.baeldung.TestHelper;

public class ResultSetToCSVUnitTest extends TestHelper {
    private static final String JDBC_URL = "jdbc:h2:mem:testDB";
    private static final String USERNAME = "dbUser";
    private static final String PASSWORD = "dbPassword";

    @BeforeEach
    void setup() throws Exception {
        connection = connect(JDBC_URL, USERNAME, PASSWORD);
        populateDB();
    }

    @AfterEach
    void tearDown() throws SQLException {
        destroyDB();
    }

    @Test
    void givenEmployeeRecordsInEmployeeTable_whenResultSetToCSVInvoked_thenReturnsCSV() throws SQLException {
        insertRecords();
        ResultSetToCSV logic = new ResultSetToCSV();
        List<String> csvRecords = logic.toCsv(connection);

        assertThat(csvRecords.size()).isEqualTo(3);

        for (int i = 1; i <= 2; i++) {
            assertThat(csvRecords.get(i - 1)).isEqualTo("\"first" + i + "\"," + "\"last" + i + "\"," + "\"" + String.format("%.1f", 100.00 * i) + "\"");
        }

        assertThat(csvRecords.get(2)).isEqualTo("\"\"\"first\\nfirst1\\nfirst2!\"\"1\"," + "\"\"\"last!\\nlast1!\"\"1\"," + "\"100.0\"");
    }

    @Test
    void givenEmployeeRecordsInEmployeeTable_whenResultSetToCSVWithOpenCsvInvoked_thenReturnsCSV() throws SQLException {
        insertRecords();
        ResultSetToCSV logic = new ResultSetToCSV();
        String csvRecords = logic.toCsvWithOpenCsv(connection);
        String[] split = csvRecords.split("\n");
        assertThat(split.length).isEqualTo(3);
        for (int i = 1; i <= 2; i++) {
            assertThat(split[i - 1]).isEqualTo("\"first" + i + "\"," + "\"last" + i + "\"," + "\"" + String.format("%.1f", 100.00 * i) + "\"");
        }

        assertThat(split[2]).isEqualTo("\"\"\"first\\nfirst1\\nfirst2!\"\"1\"," + "\"\"\"last!\\nlast1!\"\"1\"," + "\"100.0\"");
    }

    private static void insertRecords() throws SQLException {
        String sql = "INSERT INTO employees (first_name, last_name, salary) VALUES (?, ?, ?)";
        PreparedStatement statement = connection.prepareStatement(sql);
        for (int i = 1; i <= 2; i++) {
            statement.setString(1, "first" + i);
            statement.setString(2, "last" + i);
            statement.setDouble(3, 100 * i);
            statement.addBatch();
        }

        // adding records with special characters
        statement.setString(1, "\"first\nfirst1\nfirst2!\"1");
        statement.setString(2, "\"last!\nlast1!\"1");
        statement.setDouble(3, 100);
        statement.addBatch();

        statement.executeBatch();
    }
}
