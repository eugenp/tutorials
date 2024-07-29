package resultsetarraytoarraystring;
import org.junit.Test;
import static org.junit.Assert.assertArrayEquals;
import static org.junit.Assert.assertEquals;
import static org.mockito.Mockito.*;
import java.sql.*;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;

import resultsetarraytoarraystrings.NestedTestRow;
import resultsetarraytoarraystrings.StringsArrayConverter;
import resultsetarraytoarraystrings.TestRow;

public class StringArrayConverterLiveTest {

    private Connection mockConnection() throws SQLException {
        Connection conn = mock(Connection.class);
        Statement stmt = mock(Statement.class);
        ResultSet rs = mock(ResultSet.class);

        when(conn.createStatement()).thenReturn(stmt);
        when(stmt.executeQuery(anyString())).thenReturn(rs);

        return conn;
    }

    @Test
    public void givenAnResultSetArray_whenUsingGetArray_thenReturnStringArray() throws SQLException {
        Connection conn = mock(Connection.class);
        Statement stmt = mock(Statement.class);
        ResultSet rs = mock(ResultSet.class);

        // Mocking Array objects
        Array array1 = mock(Array.class);
        when(array1.getArray()).thenReturn(new String[]{"apple", "banana", "orange"});
        Array array2 = mock(Array.class);
        when(array2.getArray()).thenReturn(new String[]{"hello", "world", "java"});
        Array array3 = mock(Array.class);
        when(array3.getArray()).thenReturn(new String[]{"postgresql", "test", "example"});

        // Configuring mocks for ResultSet
        when(conn.createStatement()).thenReturn(stmt);
        when(stmt.executeQuery("SELECT id, test_array FROM test_table")).thenReturn(rs);
        when(rs.next()).thenReturn(true).thenReturn(true).thenReturn(true).thenReturn(false);
        when(rs.getArray("test_array")).thenReturn(array1).thenReturn(array2).thenReturn(array3);
        when(rs.getInt("id")).thenReturn(1).thenReturn(2).thenReturn(3);

        // Call the method under test
        List<TestRow> result = StringsArrayConverter.convertAllArraysUsingGetArray();
        // Expected result
        List<TestRow> expected = List.of(
            new TestRow(1, new String[]{"apple", "banana", "orange"}),
            new TestRow(2, new String[]{"hello", "world", "java"}),
            new TestRow(3, new String[]{"postgresql", "test", "example"})
        );

        // Asserting the result
        assertEquals(expected, result);
    }

    @Test
    public void testConvertNestedArraysToStringArray() throws SQLException {
        Connection conn = mock(Connection.class);
        Statement stmt = mock(Statement.class);
        ResultSet rs = mock(ResultSet.class);

        Array array1 = mock(Array.class);
        when(array1.getArray()).thenReturn(new Object[][] {
            { "apple", "banana" },
            { "cherry", "date" }
        });

        Array array2 = mock(Array.class);
        when(array2.getArray()).thenReturn(new Object[][] {
            { "hello", "world" },
            { "java", "programming" }
        });

        when(rs.next()).thenReturn(true).thenReturn(true).thenReturn(false);
        when(rs.getArray("nested_array")).thenReturn(array1).thenReturn(array2);

        // Mocking the connection and statement behavior
        when(conn.createStatement()).thenReturn(stmt);
        when(stmt.executeQuery("SELECT id, nested_array FROM nested_array_table")).thenReturn(rs);

        // Calling the method under test
        List<NestedTestRow> result = StringsArrayConverter.convertNestedArraysToStringArray();

        // Expected results
        List<NestedTestRow> expected = Arrays.asList(
            new NestedTestRow(1, new String[][] {
                { "apple", "banana" },
                { "cherry", "date" }
            }),
            new NestedTestRow(2, new String[][] {
                { "hello", "world" },
                { "java", "programming" }
            })
        );

        // Assertion
        assertEquals(expected, result);
    }

}
