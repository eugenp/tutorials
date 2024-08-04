package com.baeldung.resultsetarraytoarraystrings;

import java.sql.Array;
import java.sql.Connection;
import java.sql.DriverManager;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.sql.Statement;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;

public class StringsArrayConverter {

    private static final String URL = "jdbc:postgresql://localhost:5432/postgres";
    private static final String USER = "postgres";
    private static final String PASSWORD = "";

    public static Connection connect() throws SQLException {
        return DriverManager.getConnection(URL, USER, PASSWORD);
    }

    public static List<TestRow> convertAllArraysUsingGetArray() {
        List<TestRow> resultList = new ArrayList<>();
        try (Connection conn = connect(); Statement stmt = conn.createStatement()) {
            ResultSet rs = stmt.executeQuery("SELECT id, test_array FROM test_table");

            while (rs.next()) {
                int id = rs.getInt("id");
                Array array = rs.getArray("test_array");
                String[] testArray = (String[]) array.getArray();
                TestRow row = new TestRow(id, testArray);
                resultList.add(row);
            }
        } catch (SQLException e) {
            // Handle exception
        }
        return resultList;
    }

    public static List<NestedTestRow> convertNestedArraysToStringArray() {
        List<NestedTestRow> resultList = new ArrayList<>();
        try (Connection conn = connect(); Statement stmt = conn.createStatement()) {
            ResultSet rs = stmt.executeQuery("SELECT id, nested_array FROM nested_array_table");

            while (rs.next()) {
                int id = rs.getInt("id");
                Array array = rs.getArray("nested_array");
                Object[][] nestedArray = (Object[][]) array.getArray();
                String[][] stringNestedArray = Arrays.stream(nestedArray)
                    .map(subArray -> Arrays.stream(subArray)
                        .map(Object::toString)
                        .toArray(String[]::new))
                    .toArray(String[][]::new);
                NestedTestRow row = new NestedTestRow(id, stringNestedArray);
                resultList.add(row);
            }
        } catch (SQLException e) {
            // Handle exception
        }
        return resultList;
    }
}
