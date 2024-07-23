package resultsetarraytoarraystrings;

import java.sql.Array;
import java.sql.Connection;
import java.sql.DriverManager;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.sql.Statement;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;
import java.util.stream.Collectors;

public class StringsArrayConverter {

    private static final String URL = "jdbc:postgresql://localhost:5432/postgres";
    private static final String USER = "postgres";
    private static final String PASSWORD = "";

    public static Connection connect() throws SQLException {
        return DriverManager.getConnection(URL, USER, PASSWORD);
    }

    public static String[] convertAllArraysUsingGetArray() {
        List<String> resultList = new ArrayList<>();
        try (Connection conn = connect();
             Statement stmt = conn.createStatement()) {
            ResultSet rs = stmt.executeQuery("SELECT test_array FROM test_table");

            while (rs.next()) {
                Array array = rs.getArray("test_array");
                if (array != null) {
                    String[] stringArray = (String[]) array.getArray();
                    for (String element : stringArray) {
                        resultList.add(element);
                    }
                }
            }
        } catch (SQLException e) {
            e.printStackTrace();
        }

        return resultList.toArray(new String[0]);
    }


    public static String[] convertAllArraysToStringArrayUsingObject() {
        // This will store the combined result from all rows
        List<String> resultList = new ArrayList<>();
        try (Connection conn = connect();
             Statement stmt = conn.createStatement()) {
            ResultSet rs = stmt.executeQuery("SELECT test_array FROM test_table");

            while (rs.next()) {
                // Retrieve the PostgreSQL array from the ResultSet
                Array array = rs.getArray("test_array");
                if (array != null) {
                    // Convert the PostgreSQL array to Object[]
                    Object[] objArray = (Object[]) array.getArray();
                    // Convert Object[] to String[] and add to resultList
                    for (Object obj : objArray) {
                        resultList.add(obj.toString());
                    }
                }
            }
        } catch (SQLException e) {
            e.printStackTrace();
        }
        return resultList.toArray(new String[0]);
    }

    public static String[] convertNestedArraysToStringArray() {
        try (Connection conn = connect();
             Statement stmt = conn.createStatement()) {
            ResultSet rs = stmt.executeQuery("SELECT nested_array FROM nested_array_table");

            // Use Streams to process the ResultSet
            List<String> resultList = new ArrayList<>();

            while (rs.next()) {
                Array array = rs.getArray("nested_array");
                if (array != null) {
                    Object[][] nestedArray = (Object[][]) array.getArray();

                    // Flatten nested arrays using Streams
                    List<String> flattenedList = Arrays.stream(nestedArray)
                        .flatMap(subArray -> Arrays.stream(subArray)
                            .map(Object::toString))
                        .collect(Collectors.toList());

                    resultList.addAll(flattenedList);
                }
            }

            return resultList.toArray(new String[0]);
        } catch (SQLException e) {
            e.printStackTrace();
            return new String[0];
        }
    }

    public static void main (String [] args) {
        String[] arr = convertNestedArraysToStringArray();
        for (String s : arr) {
            System.out.println(s);
        }
    }
}
