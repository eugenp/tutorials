package com.baeldung.h2functions;

import org.junit.jupiter.api.AfterEach;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.params.ParameterizedTest;
import org.junit.jupiter.params.provider.Arguments;
import org.junit.jupiter.params.provider.MethodSource;

import java.io.IOException;
import java.sql.*;
import java.util.stream.Stream;

import static org.junit.jupiter.api.Assertions.*;

public class SourceCodeFunctionUnitTest {
    private static Connection connection;

    @BeforeEach
    public void setUp() throws Exception {
        connection = DriverManager.getConnection("jdbc:h2:mem:generated", "sa", "");
    }

    @AfterEach
    public void tearDown() throws SQLException {
        connection.close();
    }

    @Test
    void givenAUserDefinedFunctionWithoutArguments_whenICallTheFunction_thenIGetTheResult() throws SQLException {
        try (Statement statement = connection.createStatement()) {
            statement.execute("""
                CREATE ALIAS SAY_HELLO AS '
                    String sayHello() {
                        return "Hello, World!";
                    }
                '
                """);
        }

        try (PreparedStatement statement = connection.prepareStatement("SELECT SAY_HELLO()")) {
            ResultSet rs = statement.executeQuery();

            rs.next();
            String hello = rs.getString(1);
            assertEquals("Hello, World!", hello);
        }
    }

    @Test
    void givenAUserDefinedFunctionReferencingOtherClasses_whenICallTheFunction_thenIGetTheResult() throws SQLException {
        try (Statement statement = connection.createStatement()) {
            statement.execute("""
                CREATE ALIAS JAVA_TIME_NOW AS '
                    String javaTimeNow() {
                        return java.time.Instant.now().toString();
                    }
                '
                """);
        }

        try (PreparedStatement statement = connection.prepareStatement("SELECT JAVA_TIME_NOW()")) {
            ResultSet rs = statement.executeQuery();

            rs.next();
            String now = rs.getString(1);
            assertNotNull(now);
        }
    }

    @Test
    void givenAUserDefinedFunctionReferencingImportedClasses_whenICallTheFunction_thenIGetTheResult() throws SQLException {
        try (Statement statement = connection.createStatement()) {
            statement.execute("""
                CREATE ALIAS JAVA_TIME_NOW AS '
                    import java.time.Instant;
                    @CODE
                    String javaTimeNow() {
                        return Instant.now().toString();
                    }
                '
                """);
        }

        try (PreparedStatement statement = connection.prepareStatement("SELECT JAVA_TIME_NOW()")) {
            ResultSet rs = statement.executeQuery();

            rs.next();
            String now = rs.getString(1);
            assertNotNull(now);
        }
    }

    @ParameterizedTest
    @MethodSource("arguments")
    void givenAUserDefinedFunctionWithBoxedArguments_whenICallTheFunction_thenIGetTheResult(Integer input, Boolean output) throws SQLException {
        try (Statement statement = connection.createStatement()) {
            statement.execute("""
                CREATE ALIAS IS_ODD AS '
                    Boolean isOdd(Integer value) {
                        if (value == null) {
                            return null;
                        }
                        return (value % 2) != 0;
                    }
                '
                """);
        }

        try (PreparedStatement statement = connection.prepareStatement("SELECT IS_ODD(?)")) {
            statement.setObject(1, input);
            ResultSet rs = statement.executeQuery();

            rs.next();
            Boolean isOdd = rs.getObject(1, Boolean.class);
            assertEquals(output, isOdd);
        }
    }

    @ParameterizedTest
    @MethodSource("arguments")
    void givenAUserDefinedFunctionWithPrimitiveArguments_whenICallTheFunction_thenIGetTheResult(Integer input, Boolean output) throws SQLException {
        try (Statement statement = connection.createStatement()) {
            statement.execute("""
                CREATE ALIAS IS_ODD AS '
                    boolean isOdd(int value) {
                        return (value % 2) != 0;
                    }
                '
                """);
        }

        try (PreparedStatement statement = connection.prepareStatement("SELECT IS_ODD(?)")) {
            statement.setObject(1, input);
            ResultSet rs = statement.executeQuery();

            rs.next();
            Boolean isOdd = rs.getObject(1, Boolean.class);
            assertEquals(output, isOdd);
        }
    }

    private static Stream<Arguments> arguments() {
        return Stream.of(
                Arguments.of(1, true),
                Arguments.of(2, false),
                Arguments.of(null, null)
        );
    }


    @Test
    void givenAUserDefinedFunctionWithConnectionArgument_whenICallTheFunction_thenIGetTheResult() throws SQLException {
        try (Statement statement = connection.createStatement()) {
            statement.execute("CREATE TABLE numbers(number INTEGER NOT NULL);");
            statement.execute("INSERT INTO numbers (number) SELECT X FROM SYSTEM_RANGE(1, 20);");
            statement.execute("""
                CREATE ALIAS SUM_BETWEEN AS '
                    int sumBetween(Connection con, int lower, int higher) throws SQLException {
                        try (Statement statement = con.createStatement()) {
                            ResultSet rs = statement.executeQuery("SELECT number FROM numbers");
                            int result = 0;
                            while (rs.next()) {
                                int value = rs.getInt(1);
                                if (value > lower && value < higher) {
                                    result += value;
                                }
                            }
                            return result;
                        }
                    }
                '
                """);
        }

        try (Statement statement = connection.createStatement()) {
            ResultSet rs = statement.executeQuery("SELECT SUM_BETWEEN(5, 10)");

            rs.next();
            int value = rs.getInt(1);
            assertEquals(30, value);
        }
    }

    @Test
    void givenAUserDefinedFunctionThrowingSQLException_whenICallTheFunction_thenIGetTheException() throws SQLException {
        try (Statement statement = connection.createStatement()) {
            statement.execute("""
                CREATE ALIAS EXCEPTIONAL AS '
                    int exceptional() throws SQLException {
                        throw new SQLException("Oops");
                    }
                '
                """);
        }

        try (Statement statement = connection.createStatement()) {
            SQLException exception = assertThrows(SQLException.class,
                    () -> statement.executeQuery("SELECT EXCEPTIONAL()"));
            assertTrue(exception.getCause() instanceof SQLException);
            assertEquals("Oops", exception.getCause().getMessage());
        }
    }

    @Test
    void givenAUserDefinedFunctionThrowingRuntimeException_whenICallTheFunction_thenIGetTheWrappedException() throws SQLException {
        try (Statement statement = connection.createStatement()) {
            statement.execute("""
                CREATE ALIAS EXCEPTIONAL AS '
                    int exceptional() {
                        throw new IllegalStateException("Oops");
                    }
                '
                """);
        }

        try (Statement statement = connection.createStatement()) {
            SQLException exception = assertThrows(SQLException.class,
                    () -> statement.executeQuery("SELECT EXCEPTIONAL()"));
            assertTrue(exception.getCause() instanceof IllegalStateException);
            assertEquals("Oops", exception.getCause().getMessage());
        }
    }

    @Test
    void givenAUserDefinedFunctionThrowingCheckedException_whenICallTheFunction_thenIGetTheWrappedException() throws SQLException {
        try (Statement statement = connection.createStatement()) {
            statement.execute("""
                CREATE ALIAS EXCEPTIONAL AS '
                    import java.io.IOException;
                    @CODE
                    int exceptional() throws IOException {
                        throw new IOException("Oops");
                    }
                '
                """);
        }

        try (Statement statement = connection.createStatement()) {
            SQLException exception = assertThrows(SQLException.class,
                    () -> statement.executeQuery("SELECT EXCEPTIONAL()"));
            assertTrue(exception.getCause() instanceof IOException);
            assertEquals("Oops", exception.getCause().getMessage());
        }
    }
}
