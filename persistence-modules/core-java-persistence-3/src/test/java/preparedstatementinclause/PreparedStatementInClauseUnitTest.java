package preparedstatementinclause;

import java.sql.Connection;
import java.sql.DriverManager;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.List;
import java.util.stream.IntStream;

import org.junit.jupiter.api.AfterAll;
import org.junit.jupiter.api.Assertions;
import org.junit.jupiter.api.BeforeAll;
import org.junit.jupiter.api.Test;

import lombok.extern.slf4j.Slf4j;

@Slf4j
class PreparedStatementInClauseUnitTest {

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
    void whenPopulatingINClauseWithStringBuilder_thenIsSuccess() throws SQLException {
        ResultSet resultSet = PreparedStatementInClause.populateParamsWithStringBuilder(connection, List.of(1, 2, 3, 4, 55));
        Assertions.assertNotNull(resultSet);
        resultSet.last();
        int size = resultSet.getRow();
        Assertions.assertEquals(5, size);
    }

    @Test
    void whenPopulatingINClauseWithStream_thenIsSuccess() throws SQLException {
        ResultSet resultSet = PreparedStatementInClause.populateParamsWithStream(connection, List.of(1, 2, 3, 4, 55));
        Assertions.assertNotNull(resultSet);
        resultSet.last();
        int size = resultSet.getRow();
        Assertions.assertEquals(5, size);
    }

    @Test
    void whenPopulatingINClauseWithArray_thenIsSuccess() throws SQLException {
        ResultSet resultSet = PreparedStatementInClause.populateParamsWithArray(connection, List.of(1, 2, 3, 4, 55));
        Assertions.assertNotNull(resultSet);
        resultSet.last();
        int size = resultSet.getRow();
        Assertions.assertEquals(5, size);
    }

    private static void destroyDB() throws SQLException {
        String destroy = "DROP table IF EXISTS CUSTOMER";
        connection.prepareStatement(destroy)
            .execute();
    }

    private static void populateDB() throws SQLException {
        String createTable = "CREATE TABLE CUSTOMER (id INT, first_name VARCHAR(50), last_name VARCHAR(50))";
        connection.createStatement()
            .execute(createTable);

        String load = "INSERT INTO CUSTOMER (id, first_name, last_name) VALUES(?,?,?)";
        IntStream.rangeClosed(1, 100)
            .forEach(i -> {
                PreparedStatement preparedStatement1 = null;
                try {
                    preparedStatement1 = connection.prepareStatement(load);
                    preparedStatement1.setInt(1, i);
                    preparedStatement1.setString(2, "firstname" + i);
                    preparedStatement1.setString(3, "lastname" + i);
                    preparedStatement1.execute();
                } catch (SQLException e) {
                    throw new RuntimeException(e);
                }
            });
    }

    public static Connection connect(String url, String user, String password) throws SQLException {
        Connection connection = DriverManager.getConnection(url, user, password);
        if (connection != null) {
            log.info("Connected to database");
        }
        return connection;
    }
}