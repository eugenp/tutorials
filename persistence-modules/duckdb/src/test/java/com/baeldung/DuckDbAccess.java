package com.baeldung;

import org.junit.jupiter.api.AfterEach;
import org.junit.jupiter.api.BeforeAll;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;

import java.io.File;
import java.io.IOException;
import java.sql.Connection;
import java.sql.DriverManager;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.ResultSetMetaData;
import java.sql.SQLException;
import java.sql.Statement;
import java.util.ArrayList;
import java.util.Calendar;
import java.util.Date;
import java.util.List;

import static org.assertj.core.api.AssertionsForClassTypes.assertThat;


class DuckDbAccessIntegrationTest {

    private Connection conn = null;
    private Statement stmt = null;

    @BeforeAll
    static void setupOnce() throws Exception {
        Class.forName("org.duckdb.DuckDBDriver");
    }

    @BeforeEach
    void setup() throws SQLException {
        conn = DriverManager.getConnection("jdbc:duckdb:");
        stmt = conn.createStatement();
    }

    @Test
    void whenQueryCurrentDate_thenReturnToday() throws SQLException {
        ResultSet rs = stmt.executeQuery("SELECT current_date");
        Date currentDate = rs.next() ? rs.getDate(1) : null;

        Calendar calendar = Calendar.getInstance();
        calendar.set(Calendar.HOUR_OF_DAY, 0);
        calendar.set(Calendar.MINUTE, 0);
        calendar.set(Calendar.SECOND, 0);
        calendar.set(Calendar.MILLISECOND, 0);
        Date expectedDate = calendar.getTime();

        assertThat(currentDate).isEqualTo(expectedDate);
    }

    @Test
    void whenReadCsv_thenReturnHeaderAndData() throws SQLException {
        String filePath = getResourceAbsolutePath("/customer.csv");
        String query = String.format("SELECT * FROM read_csv('%s')", filePath);

        ResultSet rs = stmt.executeQuery(query);
        ResultSetMetaData metadata = rs.getMetaData();

        List<String> actualHeaderNames = new ArrayList<>();
        for (int n=1; n<=metadata.getColumnCount(); n++) {
            actualHeaderNames.add(metadata.getColumnLabel(n));
        }

        // Then
        List<String> expectedHeaderNames = List.of("CustomerId", "FirstName", "LastName", "Gender");
        assertThat(actualHeaderNames).isEqualTo(expectedHeaderNames);

        int rowCount = 0;
        while (rs.next()) {
            rowCount++;
        }
        assertThat(rowCount).isEqualTo(10);
    }

    @Test
    void whenImportByCsv_thenReturnCorrectRowCount() throws SQLException {
        // When
        String filePath = getResourceAbsolutePath("/customer.csv");
        String query = String.format("CREATE TABLE customer AS SELECT * FROM read_csv('%s')", filePath);
        stmt.executeUpdate(query);

        // Then
        assertThat(getTableRowCount(conn, "customer")).isEqualTo(10);
    }

    @Test
    void whenImportByJson_thenReturnCorrectRowCount() throws SQLException {
        // When
        String filePath = getResourceAbsolutePath("/product.json");
        String query = String.format("CREATE TABLE product AS SELECT * FROM read_json('%s')", filePath);
        stmt.executeUpdate(query);

        // Then
        assertThat(getTableRowCount(conn, "product")).isEqualTo(3);
    }

    @Test
    void whenImportByInsert_thenReturnCorrectRowCount() throws SQLException {
        // When
        stmt.executeUpdate("CREATE TABLE purchase(customerId BIGINT, productId BIGINT)");

        String query = "INSERT INTO purchase(customerId, productId) VALUES (?,?)";
        try (PreparedStatement pStmt = conn.prepareStatement(query)) {

            pStmt.setInt(1, 101);
            pStmt.setInt(2, 1);
            pStmt.addBatch();

            pStmt.setInt(1, 101);
            pStmt.setInt(2, 2);
            pStmt.addBatch();

            pStmt.setInt(1, 102);
            pStmt.setInt(2, 2);
            pStmt.addBatch();

            pStmt.executeBatch();
        }

        // Then
        assertThat(getTableRowCount(conn, "purchase")).isEqualTo(3);
    }

    @Test
    void whenQueryWithJoin_thenReturnCorrectCount() throws SQLException {
        String customerFilePath = getResourceAbsolutePath("/customer.csv");
        String productFilePath = getResourceAbsolutePath("/product.json");
        whenImportByInsert_thenReturnCorrectRowCount();

        String query = String.format("SELECT C.firstName, C.lastName, P.productName " +
                "FROM read_csv('%s') AS C, read_json('%s') AS P, purchase S " +
                "WHERE S.customerId = C.customerId " +
                "AND S.productId = P.productId ",
                customerFilePath, productFilePath);

        int count = 0;
        ResultSet rs = stmt.executeQuery(query);
        while (rs.next()) {
            count++;
        }

        assertThat(count).isEqualTo(3);
    }

    @Test
    void whenExportData_thenFileIsCreated() throws IOException, SQLException {
        createPurchaseView(conn);

        File tempFile = File.createTempFile("temp", "");
        String exportFilePath = tempFile.getAbsolutePath();
        String query = String.format("COPY purchase_view TO '%s'", exportFilePath);
        stmt.executeUpdate(query);

        assertThat(tempFile.length()).isGreaterThan(0);
        tempFile.delete();
    }

    @AfterEach
    void tearDown() throws SQLException {
        stmt.close();
        conn.close();
    }

    private String getResourceAbsolutePath(String name) {
        return this.getClass().getResource(name).getPath().replaceFirst("/", "");
    }

    private int getTableRowCount(Connection conn, String tableName) throws SQLException {
        try (Statement stmt = conn.createStatement()) {
            ResultSet rs = stmt.executeQuery(String.format("SELECT COUNT(*) FROM %s", tableName));
            return (rs.next()) ? rs.getInt(1) : 0;
        }
    }

    private void createPurchaseView(Connection conn) throws SQLException {
        whenImportByCsv_thenReturnCorrectRowCount();
        whenImportByJson_thenReturnCorrectRowCount();
        whenImportByInsert_thenReturnCorrectRowCount();

        String query = "CREATE VIEW purchase_view AS " +
                "SELECT P.productName, COUNT(*) AS purchaseCount " +
                "FROM customer C, product P, purchase S " +
                "WHERE S.customerId = C.customerId " +
                "AND S.productId = P.productId " +
                "GROUP BY P.productName " +
                "ORDER BY COUNT(*) DESC ";

        try (Statement stmt = conn.createStatement()) {
            stmt.executeUpdate(query);
        }
    }

}