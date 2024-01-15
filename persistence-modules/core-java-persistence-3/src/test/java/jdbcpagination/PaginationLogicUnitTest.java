package jdbcpagination;

import static org.junit.jupiter.api.Assertions.assertEquals;

import java.sql.Connection;
import java.sql.DriverManager;
import java.sql.PreparedStatement;
import java.sql.SQLException;
import java.util.List;
import java.util.concurrent.atomic.AtomicInteger;
import java.util.stream.IntStream;

import org.junit.After;
import org.junit.Before;
import org.junit.jupiter.api.AfterAll;
import org.junit.jupiter.api.AfterEach;
import org.junit.jupiter.api.BeforeAll;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;

public class PaginationLogicUnitTest {
    private static Connection connection = null;
    private static final String JDBC_URL = "jdbc:h2:mem:testDB";
    private static final String USERNAME = "dbUser";
    private static final String PASSWORD = "dbPassword";

    @BeforeAll
    public static void setup() throws Exception {
        connection = connect(JDBC_URL, USERNAME, PASSWORD);
        populateDB();
    }

    @AfterAll
    public static void tearDown() throws SQLException {
        destroyDB();
    }

    @Test
    public void givenDBPopulated_WhenReadPaginatedWithLimitAndOffset_ThenReturnsPaginatedResult() throws SQLException {
        List<List<String>> result = PaginationLogic.readAllPaginatedWithLimitAndOffset(connection, 100_000);
        assertEquals(10, result.size());

        List<String> lastBatch = result.get(result.size() - 1);
        String lastUser = lastBatch.get(lastBatch.size() - 1);
        assertEquals("firstname1000000", lastUser);
    }

    @Test
    public void givenDBPopulated_WhenReadPaginatedWithSortedKeys_ThenReturnsPaginatedResult() throws SQLException {
        List<List<String>> result = PaginationLogic.readAllPaginatedWithSortedKeys(connection, 100_000);
        assertEquals(10, result.size());

        List<String> lastBatch = result.get(result.size() - 1);
        String lastUser = lastBatch.get(lastBatch.size() - 1);
        assertEquals("firstname1000000", lastUser);
    }

    private static void destroyDB() throws SQLException {
        String destroy = """
              DROP table IF EXISTS EMPLOYEES;
            """;
        connection
            .prepareStatement(destroy)
            .execute();
    }

    private static void populateDB() throws SQLException {
        String createTable = """
            CREATE TABLE EMPLOYEES (
                id SERIAL PRIMARY KEY,
                first_name VARCHAR(50),
                last_name VARCHAR(50),
                salary DECIMAL(10, 2)
            );
            """;
        PreparedStatement preparedStatement = connection.prepareStatement(createTable);
        preparedStatement.execute();

        String load = """
            INSERT INTO EMPLOYEES (first_name, last_name, salary)
            VALUES(?,?,?)
            """;
        IntStream.rangeClosed(1,1000_000).forEach(i-> {
            PreparedStatement preparedStatement1 = null;
            try {
                preparedStatement1 = connection.prepareStatement(load);
                preparedStatement1.setString(1,"firstname"+i);
                preparedStatement1.setString(2,"lastname"+i);
                preparedStatement1.setDouble(3, 100_000+(1000_000-100_000)+Math.random());

                preparedStatement1.execute();
            } catch (SQLException e) {
                throw new RuntimeException(e);
            }
        });
    }

    public static Connection connect(String url, String user, String password) throws SQLException {
        Connection connection = DriverManager
            .getConnection(
                url,
                user,
                password
            );
        if (connection != null) {
            System.out.println("Connected to database");
        }

        return connection;
    }

}
