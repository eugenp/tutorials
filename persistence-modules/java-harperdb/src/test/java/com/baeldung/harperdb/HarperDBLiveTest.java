package com.baeldung.harperdb;

import cdata.jdbc.harperdb.HarperDBConnectionPoolDataSource;

import org.junit.jupiter.api.AfterAll;
import org.junit.jupiter.api.BeforeAll;
import org.junit.jupiter.api.Test;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.testcontainers.containers.GenericContainer;

import java.io.IOException;
import java.net.URISyntaxException;
import java.net.URL;
import java.sql.*;
import java.util.Arrays;
import java.util.Map;
import java.util.Properties;

import static org.junit.jupiter.api.Assertions.*;

public class HarperDBLiveTest {

    private static final Logger logger = LoggerFactory.getLogger(HarperDBLiveTest.class);

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

        genericContainer.start();

        port = genericContainer.getFirstMappedPort();
    }

    private static void initHarperDBApiService() {
        String url = "http://localhost:" + port + "/";
        harperDbApiService = new HarperDBApiService(url, "admin", "password");
    }

    private static void setupDB() throws IOException {
        harperDbApiService.createSchema("Demo");

        harperDbApiService.createTable("Demo", "Subject", "name");
        harperDbApiService.createTable("Demo", "Teacher", "name", "joining_date", "subject_id");
        harperDbApiService.createTable("Demo", "Teacher_Details", "teacher_id", "subject_id");

        insertSubjectRecords();
        insertTeacherRecords();
        insertTeacherDetailsRecords();
    }

    private static void insertSubjectRecords() throws IOException {
        String records = "["
            + "{\"id\":1, \"name\":\"English\"},"
            + "{\"id\":2, \"name\":\"Maths\"},"
            + "{\"id\":3, \"name\":\"Science\"}"
            + "]";

        harperDbApiService.insertRecords("Demo", "Subject", records);
    }

    private static void insertTeacherRecords() throws IOException {
        String records = "[" + "{\"id\":1, \"name\":\"James Cameron\", \"joining_date\":\"04-05-2000\"}," +
            "{\"id\":2, \"name\":\"Joe Biden\", \"joining_date\":\"20-10-2005\"}," +
            "{\"id\":3, \"name\":\"Jessie Williams\", \"joining_date\":\"04-06-1997\"}," +
            "{\"id\":4, \"name\":\"Robin Williams\", \"joining_date\":\"01-01-2020\"}," +
            "{\"id\":5, \"name\":\"Eric Johnson\", \"joining_date\":\"04-05-2022\"}," +
            "{\"id\":6, \"name\":\"Raghu Yadav\", \"joining_date\":\"02-02-1999\"}" + "]";
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
    void whenConnectionInfoInURL_thenConnectSuccess() {
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
    void whenConnectionInfoInProperties_thenConnectSuccess() {
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
    void whenConnectionPooling_thenConnectSuccess() {
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

    @Test
    void whenExecuteStoredToCreateTable_thenSuccess() throws SQLException {
        final String CREATE_TABLE_PROC = "CreateTable";
        try (Connection connection = getConnection()) {
            CallableStatement callableStatement = connection.prepareCall(CREATE_TABLE_PROC);

            callableStatement.setString("SchemaName", "prod");
            callableStatement.setString("TableName", "subject");
            callableStatement.setString("PrimaryKey", "id");
            Boolean result = callableStatement.execute();

            ResultSet resultSet = callableStatement.getResultSet();

            while (resultSet.next()) {
                String tableCreated = resultSet.getString("Success");
                assertEquals("true", tableCreated);
                logger.info("Table Created Successfully");
            }
        }
    }

    @Test
    void givenStatement_whenInsertRecord_thenSuccess() throws SQLException {
        final String INSERT_SQL = "insert into Demo.Subject(id, name) values "
            + "(4, 'Social Studies'),"
            + "(5, 'Geography')";

        try (Connection connection = getConnection()) {
            Statement statement = connection.createStatement();
            assertDoesNotThrow(() -> statement.execute(INSERT_SQL));
            assertEquals(2, statement.getUpdateCount());
        }
    }

    @Test
    void givenPrepareStatement_whenAddToBatch_thenSuccess() throws SQLException {
        final String INSERT_SQL = "insert into Demo.Teacher(id, name, joining_date) values"
            + "(?, ?, ?)";

        try (Connection connection = getConnection()) {
            PreparedStatement preparedStatement = connection.prepareStatement(INSERT_SQL);
            preparedStatement.setInt(1, 7);
            preparedStatement.setString(2, "Bret Lee");
            preparedStatement.setString(3, "07-08-2002");
            preparedStatement.addBatch();

            preparedStatement.setInt(1, 8);
            preparedStatement.setString(2, "Sarah Glimmer");
            preparedStatement.setString(3, "07-08-1997");
            preparedStatement.addBatch();

            int[] recordsInserted = preparedStatement.executeBatch();

            assertEquals(2, Arrays.stream(recordsInserted).sum());
        }
    }

    private static Connection getConnection() throws SQLException {
        String URL = "jdbc:harperdb:Server=127.0.0.1:" + port + ";User=admin;Password=password;";
        return DriverManager.getConnection(URL);
    }

    private static Connection getConnection(Map<String, String> properties) throws SQLException {
        Properties prop = new Properties();
        prop.setProperty("Server", "127.0.0.1:" + port);
        prop.setProperty("User", "admin");
        prop.setProperty("Password", "password");
        for (Map.Entry<String, String> entry : properties.entrySet()) {
            prop.setProperty(entry.getKey(), entry.getValue());
        }

        return DriverManager.getConnection("jdbc:harperdb:", prop);
    }

    @Test
    void givenStatement_whenFetchRecord_thenSuccess() throws SQLException {
        final String SQL_QUERY = "select id, name from Demo.Subject where name = 'Maths'";

        try (Connection connection = getConnection()) {
            Statement statement = connection.createStatement();
            ResultSet resultSet = statement.executeQuery(SQL_QUERY);
            while (resultSet.next()) {
                Integer id = resultSet.getInt("id");
                String name = resultSet.getString("name");
                assertNotNull(id);
                assertEquals("Maths", name);
                logger.info("Subject id:" + id + " Subject Name:" + name);
            }
        }
    }

    @Test
    void givenPreparedStatement_whenExecuteJoinQuery_thenSuccess() throws SQLException {
        final String JOIN_QUERY = "SELECT t.name as teacher_name, t.joining_date as joining_date, s.name as subject_name "
            + "from Demo.Teacher_Details AS td "
            + "INNER JOIN Demo.Teacher AS t ON t.id = td.teacher_id "
            + "INNER JOIN Demo.Subject AS s on s.id = td.subject_id "
            + "where t.name = ?";

        try (Connection connection = getConnection()) {
            PreparedStatement preparedStatement = connection.prepareStatement(JOIN_QUERY);
            preparedStatement.setString(1, "Eric Johnson");

            ResultSet resultSet = preparedStatement.executeQuery();
            while (resultSet.next()) {
                String teacherName = resultSet.getString("teacher_name");
                String subjectName = resultSet.getString("subject_name");
                String joiningDate = resultSet.getString("joining_date");
                assertEquals("Eric Johnson", teacherName);
                assertEquals("Maths", subjectName);
                logger.info("Teacher Name:" + teacherName + " Subject Name:" + subjectName + " Joining Date:" + joiningDate);
            }
        }
    }

    @Test
    void givenStatement_whenUpdateRecord_thenSuccess() throws SQLException {
        final String UPDATE_SQL = "update Demo.Teacher_Details set subject_id = 2 "
            + "where teacher_id in (2, 5)";
        final String UPDATE_SQL_WITH_SUB_QUERY = "update Demo.Teacher_Details "
            + "set subject_id = (select id from Demo.Subject where name = 'Maths') "
            + "where teacher_id in (select id from Demo.Teacher where name in ('Joe Biden', 'Eric Johnson'))";

        try (Connection connection = getConnection()) {
            Statement statement = connection.createStatement();
            assertDoesNotThrow(() -> statement.execute(UPDATE_SQL));
            assertEquals(2, statement.getUpdateCount());
        }

        try (Connection connection = getConnection()) {
            assertThrows(SQLException.class, () -> connection.createStatement().execute(UPDATE_SQL_WITH_SUB_QUERY));
        }
    }

    @Test
    void givenPreparedStatement_whenUpdateRecord_thenSuccess() throws SQLException {
        final String UPDATE_SQL = "update Demo.Teacher_Details set subject_id = ? "
            + "where teacher_id in (?, ?)";

        try (Connection connection = getConnection()) {
            PreparedStatement preparedStatement = connection.prepareStatement(UPDATE_SQL);
            preparedStatement.setInt(1, 1);
            //following is not supported by the HarperDB driver
            //Integer[] teacherIds = {4, 5};
            //Array teacherIdArray = connection.createArrayOf(Integer.class.getTypeName(), teacherIds);
            preparedStatement.setInt(2, 4);
            preparedStatement.setInt(3, 5);
            assertDoesNotThrow(() -> preparedStatement.execute());
            assertEquals(2, preparedStatement.getUpdateCount());
        }
    }

    @Test
    void givenStatement_whenDeleteRecord_thenSuccess() throws SQLException {
        final String DELETE_SQL = "delete from Demo.Teacher_Details where teacher_id = 6 and subject_id = 3";

        try (Connection connection = getConnection()) {
            Statement statement = connection.createStatement();
            assertDoesNotThrow(() -> statement.execute(DELETE_SQL));
            assertEquals(1, statement.getUpdateCount());
        }
    }

    @Test
    void givenPreparedStatement_whenDeleteRecord_thenSuccess() throws SQLException {
        final String DELETE_SQL = "delete from Demo.Teacher_Details where teacher_id = ? and subject_id = ?";

        try (Connection connection = getConnection()) {
            PreparedStatement preparedStatement = connection.prepareStatement(DELETE_SQL);
            preparedStatement.setInt(1, 6);
            preparedStatement.setInt(2, 2);
            assertDoesNotThrow(() -> preparedStatement.execute());
            assertEquals(1, preparedStatement.getUpdateCount());
        }
    }

    @Test
    void givenTempTable_whenInsertIntoSelectTempTable_thenSuccess() throws SQLException {
        try (Connection connection = getConnection()) {
            Statement statement = connection.createStatement();
            assertDoesNotThrow(() -> {
                statement.execute("insert into Teacher#TEMP(id, name, joining_date) "
                    + "values('12', 'David Flinch', '04-04-2014')");
                statement.execute("insert into Teacher#TEMP(id, name, joining_date) "
                    + "values('13', 'Stephen Hawkins', '04-07-2017')");
                statement.execute("insert into Teacher#TEMP(id, name, joining_date) "
                    + "values('14', 'Albert Einstein', '12-08-2020')");
                statement.execute("insert into Teacher#TEMP(id, name, joining_date) "
                    + "values('15', 'Leo Tolstoy', '20-08-2022')");
            });
            assertDoesNotThrow(() -> statement.execute("insert into Demo.Teacher(id, name, joining_date) "
                + "select id, name, joining_date from Teacher#TEMP"));
            ResultSet resultSet = statement.executeQuery("select count(id) as rows from Demo.Teacher where id in"
                + " (12, 13, 14, 15)");
            resultSet.next();
            int totalRows = resultSet.getInt("rows");
            logger.info("total number of rows fetched:" + totalRows);
            assertEquals(4, totalRows);
        }
    }

    @Test
    void givenUserDefinedView_whenQueryView_thenSuccess() throws SQLException {
        URL url = ClassLoader.getSystemClassLoader().getResource("UserDefinedViews.json");

        String folderPath = url.getPath().substring(0, url.getPath().lastIndexOf('/'));

        try(Connection connection = getConnection(Map.of("Location", folderPath))) {
            PreparedStatement preparedStatement = connection.prepareStatement("select teacher_name,subject_name"
                + " from UserViews.View_Teacher_Details where subject_name = ?");
            preparedStatement.setString(1, "Science");
            ResultSet resultSet = preparedStatement.executeQuery();
            while(resultSet.next()) {
                assertEquals("Science", resultSet.getString("subject_name"));
                logger.info("Science Teacher Name:" + resultSet.getString("teacher_name"));
            }
        }
    }

    @Test
    void givenAutoCache_whenQuery_thenSuccess() throws SQLException {
        URL url = ClassLoader.getSystemClassLoader().getResource("test.db");
        String folderPath = url.getPath().substring(0, url.getPath().lastIndexOf('/'));
        logger.info("Cache Location:" + folderPath);
        try(Connection connection = getConnection(Map.of("AutoCache", "true", "CacheLocation", folderPath))) {
            PreparedStatement preparedStatement = connection.prepareStatement("select id, name from Demo.Subject");

            ResultSet resultSet = preparedStatement.executeQuery();
            while(resultSet.next()) {
                logger.info("Subject Name:" + resultSet.getString("name"));
            }
        }
    }
}
