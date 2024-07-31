package jdbcpagination;

import static org.junit.jupiter.api.Assertions.assertEquals;

import java.sql.Connection;
import java.sql.DriverManager;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.ArrayList;
import java.util.List;
import java.util.stream.IntStream;

import org.junit.jupiter.api.AfterAll;
import org.junit.jupiter.api.BeforeAll;
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
        int offset = 0;  // offset is set to 0 and keep updating with pageSize
        int pageSize = 100_000;
        int totalPages = 0;
        while (true) {
            ResultSet resultSet = PaginationLogic.readPageWithLimitAndOffset(connection, offset, pageSize);
            if (!resultSet.next())
                break;
            List<String> resultPage = new ArrayList<>();

            do {
                resultPage.add(resultSet.getString("first_name"));
            } while (resultSet.next());
            assertEquals("firstname" + (resultPage.size() * (totalPages + 1)), resultPage.get(resultPage.size() - 1));
            offset += pageSize;
            totalPages++;
        }
        assertEquals(10, totalPages);
    }

    @Test
    public void givenDBPopulated_WhenReadPaginatedWithSortedKeys_ThenReturnsPaginatedResult() throws SQLException {
        // find min and max ID
        PreparedStatement preparedStatement = connection.prepareStatement("SELECT min(id) as min_id, max(id) as max_id FROM employees");
        ResultSet resultSet = preparedStatement.executeQuery();
        resultSet.next();

        int minId = resultSet.getInt("min_id");
        int maxId = resultSet.getInt("max_id");
        int lastFetchedId = 0; // assign lastFetchedId to minId

        int pageSize = 100_000;
        int totalPages = 0;

        while ((lastFetchedId + pageSize) <= maxId) {
            resultSet = PaginationLogic.readPageWithSortedKeys(connection, lastFetchedId, pageSize);
            if (!resultSet.next())
                break;
            List<String> resultPage = new ArrayList<>();
            do {
                resultPage.add(resultSet.getString("first_name"));
                lastFetchedId = resultSet.getInt("id");
            } while (resultSet.next());
            assertEquals("firstname" + (resultPage.size() * (totalPages + 1)), resultPage.get(resultPage.size() - 1));
            totalPages++;
        }
        assertEquals(10, totalPages);
    }

    private static void destroyDB() throws SQLException {
        String destroy = """
              DROP table IF EXISTS EMPLOYEES;
            """;
        connection.prepareStatement(destroy)
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
        IntStream.rangeClosed(1, 1_000_000)
            .forEach(i -> {
                PreparedStatement preparedStatement1 = null;
                try {
                    preparedStatement1 = connection.prepareStatement(load);
                    preparedStatement1.setString(1, "firstname" + i);
                    preparedStatement1.setString(2, "lastname" + i);
                    preparedStatement1.setDouble(3, 100_000 + (1_000_000 - 100_000) + Math.random());

                    preparedStatement1.execute();
                } catch (SQLException e) {
                    throw new RuntimeException(e);
                }
            });
    }

    public static Connection connect(String url, String user, String password) throws SQLException {
        Connection connection = DriverManager.getConnection(url, user, password);
        if (connection != null) {
            System.out.println("Connected to database");
        }

        return connection;
    }

}
