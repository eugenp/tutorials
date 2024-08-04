package com.baeldung.resultsetarraytoarraystring;
import org.junit.After;
import org.junit.Before;
import org.junit.Test;
import static org.junit.Assert.assertArrayEquals;
import static org.junit.Assert.assertEquals;

import java.sql.*;
import java.util.Arrays;
import java.util.List;

import com.baeldung.resultsetarraytoarraystrings.NestedTestRow;
import com.baeldung.resultsetarraytoarraystrings.StringsArrayConverter;
import com.baeldung.resultsetarraytoarraystrings.TestRow;

public class StringArrayConverterLiveTest {
    private Connection connection;
    @Before
    public void setUp() throws SQLException {
        connection = DriverManager.getConnection("jdbc:postgresql://localhost:5432/postgres", "postgres", "");
    }


    @Test
    public void givenArray_whenUsingConvertArrays_thenReturnStringArray() throws SQLException {
        // Call the method under test
        List<TestRow> result = StringsArrayConverter.convertAllArraysUsingGetArray();

        // Expected result
        String[][] expectedArrays = {
            new String[]{"apple", "banana", "orange"},
            new String[]{"hello", "world", "java"},
            new String[]{"postgresql", "test", "example"}
        };
        List<TestRow> expected = Arrays.asList(
            new TestRow(1, expectedArrays[0]),
            new TestRow(2, expectedArrays[1]),
            new TestRow(3, expectedArrays[2])
        );

        // Compare each TestRow's array with the expected array
        for (int i = 0; i < result.size(); i++) {
            assertArrayEquals(expected.get(i).getTestArray(), result.get(i).getTestArray());
        }
    }

    @Test
    public void givenNestedArray_whenUsingConvertNestedArrays_thenReturnStringNestedArray() throws SQLException {
        // Call the method under test
        List<NestedTestRow> result = StringsArrayConverter.convertNestedArraysToStringArray();

        // Expected results
        String[][][] expectedNestedArrays = {
            {
                { "apple", "banana" },
                { "cherry", "date" }
            },
            {
                { "hello", "world" },
                { "java", "programming" }
            }
        };
        List<NestedTestRow> expected = Arrays.asList(
            new NestedTestRow(1, expectedNestedArrays[0]),
            new NestedTestRow(2, expectedNestedArrays[1])
        );

        // Compare each NestedTestRow's array with the expected array
        for (int i = 0; i < result.size(); i++) {
            assertArrayEquals(expected.get(i).getNestedArray(), result.get(i).getNestedArray());
        }
    }

    @After
    public void tearDown() throws SQLException {
        if (connection != null && !connection.isClosed()) {
            connection.close();
        }
    }

}
