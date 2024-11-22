package com.baeldung.resultset;

import org.apache.poi.ss.usermodel.Sheet;
import org.apache.poi.ss.usermodel.Workbook;
import org.apache.poi.xssf.usermodel.XSSFWorkbook;
import org.junit.jupiter.api.AfterAll;
import org.junit.jupiter.api.BeforeAll;
import org.junit.jupiter.api.Test;

import java.io.*;
import java.sql.Connection;
import java.sql.DriverManager;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.sql.Statement;

import static org.assertj.core.api.Assertions.assertThat;

class ResultSetExcelUnitTest {

    private static final String JDBC_CONNECTION_URL = "jdbc:h2:mem:testdb";
    private static final String JDBC_USERNAME = "sa";
    private static final String JDBC_PASSWORD = "";

    private static DataPreparer dataPreparer;
    private static Connection connection;


    private static Connection getConnection() throws SQLException {
        return DriverManager.getConnection(JDBC_CONNECTION_URL, JDBC_USERNAME, JDBC_PASSWORD);
    }

    @BeforeAll
    static void setup() throws SQLException {
        connection = getConnection();
        dataPreparer = new DataPreparer(connection);
        dataPreparer.prepare();
    }

    @Test
    void whenExportDataToExcel_thenFileContainsCorrectData() throws IOException, SQLException {
        Workbook workbook = new XSSFWorkbook();

        try (Connection connection = getConnection();
             Statement statement = connection.createStatement();
             ResultSet rs = statement.executeQuery(dataPreparer.getSelectSql());) {

            ResultSet2Workbook conversion = new ResultSet2Workbook(rs);
            conversion.write(workbook);
        }

        // Write the workbook to a file
        File excelFile = File.createTempFile("test", ".xlsx");
        try (OutputStream outputStream = new BufferedOutputStream(new FileOutputStream(excelFile))) {
            workbook.write(outputStream);
            workbook.close();
        }

        assertExcelFileContent(excelFile);
    }

    private void assertExcelFileContent(File excelFile) throws IOException {
        try (InputStream inputStream = new FileInputStream(excelFile);
             Workbook readWorkbook = new XSSFWorkbook(inputStream)) {
            Sheet sheet = readWorkbook.getSheetAt(0);
            assertThat(sheet.getPhysicalNumberOfRows()).isEqualTo(6);
        }
    }

    @AfterAll
    static void tearDown() throws SQLException {
        if (connection != null && !connection.isClosed()) {
            connection.close();
        }
    }

}