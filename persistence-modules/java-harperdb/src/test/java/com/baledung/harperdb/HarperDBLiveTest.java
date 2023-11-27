package com.baledung.harperdb;

import cdata.jdbc.harperdb.HarperDBConnectionPoolDataSource;

import org.junit.jupiter.api.AfterAll;
import org.junit.jupiter.api.BeforeAll;
import org.junit.jupiter.api.Test;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.testcontainers.containers.GenericContainer;

import java.io.IOException;
import java.net.URISyntaxException;
import java.sql.*;
import java.util.Properties;

import static org.junit.jupiter.api.Assertions.*;

public class HarperDBLiveTest {

    private static final Logger logger = LoggerFactory.getLogger(HarperDBLiveTest.class);

    private static String host;

    private static Integer port;

    private static HarperDBContainer harperDBContainer;
    private static HarperDBApiService harperDbApiService;

    @BeforeAll
    static void setupHarperDB() throws IOException, InterruptedException, URISyntaxException {
        installHarperDB();
        initHarperDBApiService();
        setupDB();
    }

    private static void installHarperDB() {
        harperDBContainer = new HarperDBContainer();

        GenericContainer genericContainer = harperDBContainer.installHarperDB();

        host = genericContainer.getHost();
        genericContainer.start();

        port = genericContainer.getFirstMappedPort();
    }

    private static void initHarperDBApiService() {
        String url = "http://localhost:" + port + "/";
        harperDbApiService = new HarperDBApiService(url, "admin", "password");
    }

    private static void setupDB() throws URISyntaxException, IOException {
        harperDbApiService.createSchema("Demo");

        harperDbApiService.createTable("Demo", "Subject", "name");
        harperDbApiService.createTable("Demo", "Teacher", "name", "joining_date", "subject_id");
        harperDbApiService.createTable("Demo", "Teacher_Details", "teacher_id", "subject_id");

        insertSubjectRecords();
        insertTeacherRecords();
        insertTeacherDetailsRecords();
    }

    private static void insertSubjectRecords() throws IOException {
        String records = "[" + "{\"id\":1, \"name\":\"English\"}," + "{\"id\":2, \"name\":\"Maths\"}," + "{\"id\":3, \"name\":\"Science\"}" + "]";

        harperDbApiService.insertRecords("Demo", "Subject", records);
    }

    private static void insertTeacherRecords() throws IOException {
        String records = "[" + "{\"id\":1, \"name\":\"Parthiv Pradhan\", \"joining_date\":\"04-05-2000\"}," +
            "{\"id\":2, \"name\":\"David Martinez\", \"joining_date\":\"20-10-2005\"}," +
            "{\"id\":3, \"name\":\"Liam Williams\", \"joining_date\":\"04-06-1997\"}," +
            "{\"id\":4, \"name\":\"Robin Williams\", \"joining_date\":\"01-01-2020\"}," +
            "{\"id\":5, \"name\":\"Eric Martin\", \"joining_date\":\"04-05-2022\"}," +
            "{\"id\":6, \"name\":\"Sajjan Nagendra\", \"joining_date\":\"02-02-1999\"}" + "]";
        harperDbApiService.insertRecords("Demo", "Teacher", records);
    }

    private static void insertTeacherDetailsRecords() throws IOException {
        String records = "[" + "{\"id\":1, \"teacher_id\":1, \"subject_id\":1}," + "{\"id\":2, \"teacher_id\":1, \"subject_id\":2}," +
            "{\"id\":3, \"teacher_id\":2, \"subject_id\":3 }," + "{\"id\":4, \"teacher_id\":3, \"subject_id\":1}," +
            "{\"id\":5, \"teacher_id\":3, \"subject_id\":3}," + "{\"id\":6, \"teacher_id\":4, \"subject_id\":2}," +
            "{\"id\":7, \"teacher_id\":5, \"subject_id\":3}," + "{\"id\":8, \"teacher_id\":6, \"subject_id\":1}," +
            "{\"id\":9, \"teacher_id\":6, \"subject_id\":2}," + "{\"id\":15, \"teacher_id\":6, \"subject_id\":3}" + "]";

        harperDbApiService.insertRecords("Demo", "Teacher_Details", records);
    }

    @AfterAll
    static void stopHarperDB() {
        harperDBContainer.stop();
    }

    @Test
    void whenConnectionInfoInURL_thenConnectSuccess() throws SQLException {
        assertDoesNotThrow(() -> {
            final String JDBC_URL = "jdbc:harperdb:Server=127.0.0.1:" + port + ";User=admin;Password=password;";

            try (Connection connection = DriverManager.getConnection(JDBC_URL)) {
                connection.createStatement()
                    .executeQuery("select 1");
                logger.info("Connection Successful");
            }
        });
    }

    @Test
    void whenConnectionInfoInProperties_thenConnectSuccess() throws SQLException {
        assertDoesNotThrow(() -> {
            Properties prop = new Properties();
            prop.setProperty("Server", "127.0.0.1:" + port);
            prop.setProperty("User", "admin");
            prop.setProperty("Password", "password");

            try (Connection connection = DriverManager.getConnection("jdbc:harperdb:", prop)) {
                connection.createStatement()
                    .executeQuery("select 1");
                logger.info("Connection Successful");
            }
        });
    }

    @Test
    void whenConnectionPooling_thenConnectSuccess() throws SQLException {
        assertDoesNotThrow(() -> {
            HarperDBConnectionPoolDataSource harperdbPoolDataSource = new HarperDBConnectionPoolDataSource();
            final String JDBC_URL = "jdbc:harperdb:UseConnectionPooling=true;PoolMaxSize=2;Server=127.0.0.1:" + port + ";User=admin;Password=password;";
            harperdbPoolDataSource.setURL(JDBC_URL);

            try (Connection connection = harperdbPoolDataSource.getPooledConnection()
                .getConnection()) {
                connection.createStatement()
                    .executeQuery("select 1");
                logger.info("Connection Pool Successful");
            }
        });
    }

    private static Connection getConnection() throws SQLException {
        String URL = "jdbc:harperdb:Server=127.0.0.1:" + port + ";User=admin;Password=password;";
        return DriverManager.getConnection(URL);
    }

    @Test
    void givenStatement_whenFetchRecord_thenSuccess() throws SQLException {
        final String SQL_QUERY = "select id, name, grade from Demo.student";

        try (Connection connection = getConnection()) {
            Statement statement = connection.createStatement();
            ResultSet resultSet = statement.executeQuery(SQL_QUERY);
            while (resultSet.next()) {
                int id = resultSet.getInt("id");
                String name = resultSet.getString("name");
                int grade = resultSet.getInt("grade");
                assertNotNull(Integer.valueOf(id));
                logger.info("Student id:" + id + " Student Name:" + name + " grade:" + grade);
            }
        }
    }

    @Test
    void givenStatement_whenInsertRecord_thenSuccess() throws SQLException {
        final String INSERT_SQL = "insert into Demo.student(id, name, grade) values (10, 'Barak', 3)";

        try (Connection connection = getConnection()) {
            Statement statement = connection.createStatement();
            assertDoesNotThrow(() -> statement.execute(INSERT_SQL));
        }
    }

    @Test
    void givenStatement_whenInsertRecordInTableWithoutAttributes_thenSuccess() throws SQLException {
        final String INSERT_SQL =
            "insert into Demo.teacher(id, name, joining_date) " + "values (7, 'David Sirocco', '04-05-2004'), (8, 'Ali Azmat', '04-10-2000')";
        final String QUERY_SQL = "select name, joining_date from Demo.teacher where name = ?";

        try (Connection connection = getConnection()) {
            Statement statement = connection.createStatement();
            assertDoesNotThrow(() -> statement.execute(INSERT_SQL));
            PreparedStatement preparedStatement = connection.prepareStatement(QUERY_SQL);
            preparedStatement.setString(1, "David Sirocco");
            ResultSet resultSet = preparedStatement.executeQuery();
            while (resultSet.next()) {
                String teacherName = resultSet.getString("name");
                String joinDate = resultSet.getString("joining_date");
                assertEquals("David Sirocco", teacherName);
                assertEquals("04-05-2004", joinDate);
                logger.info("name:" + teacherName + " joining date:" + joinDate);
            }
        }
    }

    @Test
    void givenStatement_whenUpdateRecord_thenSuccess() throws SQLException {
        final String UPDATE_SQL = "update Demo.student set grade = 4 where id = 5";

        try (Connection connection = getConnection()) {
            Statement statement = connection.createStatement();
            assertDoesNotThrow(() -> statement.execute(UPDATE_SQL));
            assertEquals(1, statement.getUpdateCount());
        }
    }

    @Test
    void givenStatement_whenDeleteRecord_thenSuccess() throws SQLException {
        final String DELETE_SQL = "delete from Demo.student where id = 3";

        try (Connection connection = getConnection()) {
            Statement statement = connection.createStatement();
            assertDoesNotThrow(() -> statement.execute(DELETE_SQL));
            assertEquals(1, statement.getUpdateCount());
        }
    }

    @Test
    void givenPreparedStatement_whenFetchRecord_thenSuccess() throws SQLException {
        final String SQL_QUERY = "select id, name, grade from Demo.student where name = ? and grade = ?";

        try (Connection connection = getConnection()) {
            PreparedStatement preparedStatement = connection.prepareStatement(SQL_QUERY);
            preparedStatement.setString(1, "Robin");
            preparedStatement.setInt(2, 4);
            ResultSet resultSet = preparedStatement.executeQuery();
            while (resultSet.next()) {
                int id = resultSet.getInt("id");
                String name = resultSet.getString("name");
                int grade = resultSet.getInt("grade");
                assertNotNull(Integer.valueOf(id));
                assertEquals("Robin", name);
                logger.info("Student id:" + id + " Student Name:" + name + " grade:" + grade);
            }
        }
    }

    @Test
    void givenPreparedStatement_whenDeleteRecord_thenSuccess() throws SQLException {
        final String DELETE_SQL = "delete from Demo.student where name = ?";

        try (Connection connection = getConnection()) {
            PreparedStatement preparedStatement = connection.prepareStatement(DELETE_SQL);
            preparedStatement.setString(1, "James");
            assertDoesNotThrow(() -> preparedStatement.execute());
            assertEquals(1, preparedStatement.getUpdateCount());
        }
    }

    @Test
    void givenPreparedStatement_whenUpdateRecord_thenSuccess() throws SQLException {
        final String UPDATE_SQL = "update Demo.student set grade = ? where id = ? and name = ?";

        try (Connection connection = getConnection()) {
            PreparedStatement preparedStatement = connection.prepareStatement(UPDATE_SQL);
            preparedStatement.setInt(1, 5);
            preparedStatement.setInt(2, 1);
            preparedStatement.setString(3, "John");
            assertDoesNotThrow(() -> preparedStatement.execute());
            assertEquals(1, preparedStatement.getUpdateCount());
        }
    }

    //@Test
    void whenAddtoBatch_thenExecuteBatchIsSuccess() throws SQLException {
        final String INSERT_SQL = "insert into Demo.teacher(id, name, joining_date, subject_id)" + "values(?, ?, ?, ?)";

        try (Connection connection = getConnection()) {
            PreparedStatement preparedStatement = connection.prepareStatement(INSERT_SQL);
            preparedStatement.setInt(1, 9);
            preparedStatement.setString(2, "Bret Lee");
            preparedStatement.setString(3, "07-08-2002");
            preparedStatement.setString(4, "[1, 3]");
            preparedStatement.addBatch();

            preparedStatement.setInt(1, 10);
            preparedStatement.setString(2, "Sarah Glimmer");
            preparedStatement.setString(3, "07-08-1997");
            preparedStatement.setString(4, "[1, 2]");

            preparedStatement.addBatch();

            assertDoesNotThrow(() -> preparedStatement.executeBatch());
        }
    }

    @Test
    void whenExecuteJoinQuery_thenResultSuccess() throws SQLException {
        final String JOIN_QUERY =
            "SELECT t.name as teacher_name, s.name as subject_name " + "from Demo.teacher AS t INNER JOIN Demo.subject AS s ON t.subject_id = s.id";

        try (Connection connection = getConnection()) {
            Statement statement = connection.createStatement();

            ResultSet resultSet = statement.executeQuery(JOIN_QUERY);
            while (resultSet.next()) {
                logger.info("Teacher Name:" + resultSet.getString("teacher_name") + " Subject Name:" + resultSet.getString("subject_name"));
            }

        }

    }

    @Test
    void whenExecuteStoredToCreateTable_thenSuccess() throws SQLException {
        final String CREATE_TABLE_SQL = "CreateTable";
        try (Connection connection = getConnection()) {
            CallableStatement callableStatement = connection.prepareCall(CREATE_TABLE_SQL);

            callableStatement.setString("SchemaName", "prod"); //schema gets created too
            callableStatement.setString("TableName", "subject");
            callableStatement.setString("PrimaryKey", "id");
            Boolean result = callableStatement.execute();

            ResultSet resultSet = callableStatement.getResultSet();

            while (resultSet.next()) {
                String tableCreated = resultSet.getString("Success");
                assertEquals("true", tableCreated);
                logger.info("result of the callable execute:" + tableCreated);
            }
        }
    }
}
