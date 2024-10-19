package com.baeldung.resultsettocsv;

import java.io.StringWriter;
import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.ResultSetMetaData;
import java.sql.SQLException;
import java.util.ArrayList;
import java.util.List;

import com.opencsv.CSVWriter;

public class ResultSetToCSV {
    public List<String> toCsv(Connection connection) throws SQLException {
        List<String> csvRecords = new ArrayList<>();
        PreparedStatement preparedStatement = connection.prepareStatement("SELECT * FROM employees");
        ResultSet resultSet = preparedStatement.executeQuery();
        while (resultSet.next()) {
            String eachRecord = formatForCsv(resultSet.getString("first_name")) + "," + formatForCsv(resultSet.getString("last_name")) + "," + "\"" + resultSet.getDouble("salary") + "\"";
            csvRecords.add(eachRecord);
        }
        return csvRecords;
    }

    private String formatForCsv(String value) {
        return "\"" + value.replace("\n", "\\n")
            .replace("\r", "\\r")
            .replace("\"", "\"\"") + "\"";
    }

    public String toCsvWithOpenCsv(Connection connection) throws SQLException {
        PreparedStatement preparedStatement = connection.prepareStatement("SELECT * FROM employees");
        ResultSet resultSet = preparedStatement.executeQuery();
        StringWriter stringWriter = new StringWriter();
        CSVWriter csvWriter = new CSVWriter(stringWriter, CSVWriter.DEFAULT_SEPARATOR, CSVWriter.DEFAULT_QUOTE_CHARACTER,  // Default quote character is double quote
            CSVWriter.DEFAULT_ESCAPE_CHARACTER, // Default escape character is double quote
            CSVWriter.DEFAULT_LINE_END);

        ResultSetMetaData metaData = resultSet.getMetaData();
        int columnCount = metaData.getColumnCount();
        String[] row = new String[columnCount - 1];

        while (resultSet.next()) {
            row[0] = resultSet.getString("first_name")
                .replace("\n", "\\n")
                .replace("\r", "\\r");

            row[1] = resultSet.getString("last_name")
                .replace("\n", "\\n")
                .replace("\r", "\\r");

            row[2] = String.valueOf(resultSet.getDouble("salary"));

            csvWriter.writeNext(row);
        }
        return stringWriter.toString();
    }
}
