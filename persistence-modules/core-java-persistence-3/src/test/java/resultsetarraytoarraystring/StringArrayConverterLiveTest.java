package resultsetarraytoarraystring;
import org.junit.Test;
import static org.junit.Assert.assertArrayEquals;
import static org.mockito.Mockito.*;
import java.sql.*;
import java.util.ArrayList;
import java.util.List;

import resultsetarraytoarraystrings.StringsArrayConverter;

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
        String[] expected = {"apple", "banana", "orange", "hello", "world", "java", "postgresql", "test", "example"};

        Connection conn = mockConnection();
        Statement stmt = conn.createStatement();
        ResultSet rs = stmt.executeQuery("SELECT test_array FROM test_table");

        Array array1 = mock(Array.class);
        when(array1.getArray()).thenReturn(new String[]{"apple", "banana", "orange"});
        Array array2 = mock(Array.class);
        when(array2.getArray()).thenReturn(new String[]{"hello", "world", "java"});
        Array array3 = mock(Array.class);
        when(array3.getArray()).thenReturn(new String[]{"postgresql", "test", "example"});

        when(rs.next()).thenReturn(true).thenReturn(true).thenReturn(true).thenReturn(false);
        when(rs.getArray("test_array")).thenReturn(array1).thenReturn(array2).thenReturn(array3);
        String[] result = StringsArrayConverter.convertAllArraysUsingGetArray();
        assertArrayEquals(expected, result);
    }

    @Test
    public void testConvertAllArraysToStringArrayUsingObject() throws SQLException {
        String[] expected = {"apple", "banana", "orange", "hello", "world", "java", "postgresql", "test", "example"};

        Connection conn = mockConnection();
        Statement stmt = conn.createStatement();
        ResultSet rs = stmt.executeQuery("SELECT test_array FROM test_table");

        Array array1 = mock(Array.class);
        when(array1.getArray()).thenReturn(new Object[]{"apple", "banana", "orange"});
        Array array2 = mock(Array.class);
        when(array2.getArray()).thenReturn(new Object[]{"hello", "world", "java"});
        Array array3 = mock(Array.class);
        when(array3.getArray()).thenReturn(new Object[]{"postgresql", "test", "example"});

        when(rs.next()).thenReturn(true).thenReturn(true).thenReturn(true).thenReturn(false);
        when(rs.getArray("test_array")).thenReturn(array1).thenReturn(array2).thenReturn(array3);

        String[] result = StringsArrayConverter.convertAllArraysToStringArrayUsingObject();
        assertArrayEquals(expected, result);
    }

    @Test
    public void testConvertNestedArraysToStringArray() throws SQLException {
        String[] expected = {"apple", "banana", "cherry", "date", "hello", "world", "java", "programming"};

        Connection conn = mockConnection();
        Statement stmt = conn.createStatement();
        ResultSet rs = stmt.executeQuery("SELECT nested_array FROM nested_array_table");

        Array array1 = mock(Array.class);
        when(array1.getArray()).thenReturn(new Object[][]{
            {"apple", "banana"}, {"cherry", "date"}
        });
        Array array2 = mock(Array.class);
        when(array2.getArray()).thenReturn(new Object[][]{
            {"hello", "world"}, {"java", "programming"}
        });

        when(rs.next()).thenReturn(true).thenReturn(true).thenReturn(false);
        when(rs.getArray("nested_array")).thenReturn(array1).thenReturn(array2);

        String[] result = StringsArrayConverter.convertNestedArraysToStringArray();
        assertArrayEquals(expected, result);
    }

}
