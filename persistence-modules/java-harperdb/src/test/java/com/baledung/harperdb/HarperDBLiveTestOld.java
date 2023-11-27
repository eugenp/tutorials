package com.baledung.harperdb;

import cdata.jdbc.harperdb.HarperDBConnectionPoolDataSource;
import com.github.dockerjava.zerodep.shaded.org.apache.hc.client5.http.classic.methods.HttpPost;
import com.github.dockerjava.zerodep.shaded.org.apache.hc.client5.http.impl.classic.CloseableHttpClient;
import com.github.dockerjava.zerodep.shaded.org.apache.hc.client5.http.impl.classic.CloseableHttpResponse;
import com.github.dockerjava.zerodep.shaded.org.apache.hc.client5.http.impl.classic.HttpClientBuilder;
import com.github.dockerjava.zerodep.shaded.org.apache.hc.core5.http.ClassicHttpRequest;
import com.github.dockerjava.zerodep.shaded.org.apache.hc.core5.http.HttpEntity;
import com.github.dockerjava.zerodep.shaded.org.apache.hc.core5.http.io.entity.StringEntity;
import org.junit.jupiter.api.AfterAll;
import org.junit.jupiter.api.BeforeAll;
import org.junit.jupiter.api.Test;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.testcontainers.containers.GenericContainer;

import java.io.IOException;
import java.net.URISyntaxException;
import java.sql.*;
import java.util.Map;
import java.util.Properties;

import static org.junit.jupiter.api.Assertions.*;

public class HarperDBLiveTestOld {
    private static final Logger logger = LoggerFactory.getLogger(HarperDBLiveTestOld.class);

    private static String host;

    private static Integer port;

    private static GenericContainer harperDBContainer;

    @BeforeAll
    static void setupHarperDB() throws IOException, InterruptedException, URISyntaxException {
        installHarperDB();
        setupDB();
    }

    private static void installHarperDB() {
        Map<String, String> envMap = Map.of(
                "HDB_ADMIN_USERNAME", "admin",
                "HDB_ADMIN_PASSWORD", "password",
                "OPERATIONSAPI_NETWORK_PORT", "9925",
                "ROOTPATH", "/home/harperdb/hdb",
                "LOGGING_STDSTREAMS", "true"
        );

        harperDBContainer = new GenericContainer("harperdb/harperdb:latest")
                .withEnv(envMap)
                .withExposedPorts(9925, 9926);
        host = harperDBContainer.getHost();
        harperDBContainer.start();
        port = harperDBContainer.getFirstMappedPort();
        logger.info("DB Host:" + host + "\n DB Port:" + port);
    }

    private static void setupDB() throws URISyntaxException, IOException {
        setupSchema("demo");
        createTable("demo", "student", "name", "grade", "subject_id");
        createTable("demo", "teacher", "name", "joining_date", "subject_id");
        insertRecords();
    }

    private static void setupSchema(String schema) throws URISyntaxException, IOException {
        String requestBody = "{\"operation\":\"create_schema\", \"" + "schema\":\"" + schema + "\"" + "}";
        executeHttpPostRequest(requestBody);
    }

    private static void executeHttpPostRequest(String httpRequest) throws IOException {
        logger.info("Post request body:" + httpRequest);

        try (CloseableHttpClient closeableHttpClient = HttpClientBuilder.create().build()) {
            HttpPost request = new HttpPost("http://localhost:" + port + "/");
            request.addHeader("Authorization", "Basic YWRtaW46cGFzc3dvcmQ=");
            request.addHeader("Content-Type", "application/json");
            request.setEntity((HttpEntity) new StringEntity(httpRequest));
            CloseableHttpResponse response = closeableHttpClient.execute((ClassicHttpRequest) request);
            logger.info("REST API response:" + response.toString());
            assertEquals(200, response.getCode());
        }
    }

    private void insertStudentRecords() {

    }

    private void insertSubjectRecords() {

    }

    private void insertTeacherRecords() {

    }

    private static void createTable(String schemaName, String tableName, String... attributes) throws IOException {
        String createTableReq = "{\"operation\":\"create_table\",\"schema\":\""
                + schemaName + "\",\"table\":\""
                + tableName + "\",\"hash_attribute\":\"id\""
                + "}";
        executeHttpPostRequest(createTableReq);
        logger.info("created table:" + tableName);
        for (String attribute : attributes) {
            String createAttrReq = "{\"operation\":\"create_attribute\",\"schema\":\""
                    + schemaName + "\",\"table\":\""
                    + tableName + "\",\"attribute\":\""
                    + attribute + "\""
                    + "}";
            executeHttpPostRequest(createAttrReq);
            logger.info("created attribute:" + attribute + " in table:" + tableName);
        }
    }

    private static void insertRecords() throws IOException {

        String table = "student";
        String schema = "demo";
        String operation = "insert";
        String records = "[" +
                "{\"id\":1, \"name\":\"John\", \"grade\":4},"
                + "{\"id\":2, \"name\":\"James\", \"grade\":1},"
                + "{\"id\":3, \"name\":\"Williams\", \"grade\":1},"
                + "{\"id\":4, \"name\":\"Robin\", \"grade\":4},"
                + "{\"id\":5, \"name\":\"Torry\", \"grade\":3},"
                + "{\"id\":6, \"name\":\"Nancy\", \"grade\":5},"
                + "{\"id\":7, \"name\":\"Mary\", \"grade\":5},"
                + "{\"id\":8, \"name\":\"Jenny\", \"grade\":6},"
                + "{\"id\":9, \"name\":\"Katy\", \"grade\":6},"
                + "{\"id\":15, \"name\":\"Michael\", \"grade\":6}"
        + "]";
        String requestBody = "{\"table\":" + "\"" + table + "\","
                + "\"schema\":" + "\"" + schema + "\"" + ","
                + "\"operation\":" + "\"" + operation + "\"" + ","
                + "\"records\":" + records
        + "}";
        String sql = "{"
                + "\"operation\":\"sql\", "
                + "\"sql\":\"insert into demo.student(id, name, grade) values"
                + "(1, 'John', 4), (2, 'James', 1), (3, 'Williams', 1), (4, 'Robin', 4), (5, 'Torry', 3),"
                + " (6, 'Nancy', 5), (7, 'Mary', 5), (8, 'Jenny', 6), (9, 'Katy', 6)\""
        + "}";
        executeHttpPostRequest(requestBody);
    }

    @AfterAll
    static void stopHarperDB() {
        harperDBContainer.stop();
    }

    @Test
    void whenConnectionInfoInURL_thenConnectSuccess() throws SQLException {
        assertDoesNotThrow(() -> {
            String URL = "jdbc:harperdb:Server=127.0.0.1:" + port + ";User=admin;Password=password;";

            try (Connection connection = DriverManager.getConnection(URL)) {
                connection.createStatement().executeQuery("select 1");
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
                connection.createStatement().executeQuery("select 1");
                logger.info("Connection Successful");
            }
        });
    }

    @Test
    void whenConnectionPooling_thenConnectSuccess() throws SQLException {
        assertDoesNotThrow(() -> {
            HarperDBConnectionPoolDataSource harperdbPoolDataSource = new HarperDBConnectionPoolDataSource();
            String URL = "jdbc:harperdb:UseConnectionPooling=true;PoolMaxSize=2;Server=127.0.0.1:" + port
					+ ";User=admin;Password=1234;";
            harperdbPoolDataSource.setURL(URL);

            try(Connection connection = harperdbPoolDataSource.getPooledConnection().getConnection()) {
                connection.createStatement().executeQuery("select 1");
                logger.info("Connection Successful");
            }
        });
    }

    private static Connection getConnection() throws SQLException {
        String URL = "jdbc:harperdb:Server=127.0.0.1:" + port + ";User=admin;Password=password;";
        return DriverManager.getConnection(URL);
    }

    @Test
    void givenStatement_whenFetchRecord_thenSuccess() throws SQLException {
        final String SQL_QUERY = "select id, name, grade from demo.student";

        try(Connection connection = getConnection()) {
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
        final String INSERT_SQL = "insert into demo.student(id, name, grade) values (10, 'Barak', 3)";

        try(Connection connection = getConnection()) {
            Statement statement = connection.createStatement();
            assertDoesNotThrow(() -> statement.execute(INSERT_SQL));
        }
    }

    @Test
    void givenStatement_whenInsertRecordInTableWithoutAttributes_thenSuccess() throws SQLException {
        final String INSERT_SQL = "insert into demo.teacher(id, name, joining_date) "
				+ "values (1, 'David Martinez', '04-05-2004'), (2, 'Eric Martin', '04-10-2000')";
        final String QUERY_SQL = "select name, joining_date from demo.teacher where name = ?";

        try(Connection connection = getConnection()) {
            Statement statement = connection.createStatement();
            assertDoesNotThrow(() -> statement.execute(INSERT_SQL));
            PreparedStatement preparedStatement = connection.prepareStatement(QUERY_SQL);
            preparedStatement.setString(1, "Eric Martin");
            ResultSet resultSet = preparedStatement.executeQuery();
            while (resultSet.next()) {
                String teacherName = resultSet.getString("name");
                String joinDate = resultSet.getString("joining_date");
                assertEquals("Eric Martin", teacherName);
                assertEquals("04-10-2000", joinDate);
                logger.info("name:" + teacherName + " joining date:" + joinDate);
            }
        }
    }

    @Test
    void givenStatement_whenUpdateRecord_thenSuccess() throws SQLException {
        final String UPDATE_SQL = "update demo.student set grade = 4 where id = 5";

        try(Connection connection = getConnection()) {
            Statement statement = connection.createStatement();
            assertDoesNotThrow(() -> statement.execute(UPDATE_SQL));
            assertEquals(1, statement.getUpdateCount());
        }
    }

    @Test
    void givenStatement_whenDeleteRecord_thenSuccess() throws SQLException {
        final String DELETE_SQL = "delete from demo.student where id = 3";

        try(Connection connection = getConnection()) {
            Statement statement = connection.createStatement();
            assertDoesNotThrow(() -> statement.execute(DELETE_SQL));
            assertEquals(1, statement.getUpdateCount());
        }
    }

    @Test
    void givenPreparedStatement_whenFetchRecord_thenSuccess() throws SQLException {
        final String SQL_QUERY = "select id, name, grade from demo.student where name = ? and grade = ?";

        try(Connection connection = getConnection()) {
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
        final String DELETE_SQL = "delete from demo.student where name = ?";

        try(Connection connection = getConnection()) {
            PreparedStatement preparedStatement = connection.prepareStatement(DELETE_SQL);
            preparedStatement.setString(1, "James");
            assertDoesNotThrow(() -> preparedStatement.execute());
            assertEquals(1, preparedStatement.getUpdateCount());
        }
    }

    @Test
    void givenPreparedStatement_whenUpdateRecord_thenSuccess() throws SQLException {
        final String UPDATE_SQL = "update demo.student set grade = ? where id = ? and name = ?";

        try(Connection connection = getConnection()) {
            PreparedStatement preparedStatement = connection.prepareStatement(UPDATE_SQL);
            preparedStatement.setInt(1, 5);
            preparedStatement.setInt(2, 1);
            preparedStatement.setString(3, "John");
            assertDoesNotThrow(() -> preparedStatement.execute());
            assertEquals(1, preparedStatement.getUpdateCount());
        }
    }

    @Test
    void whenAddtoBatch_thenExecuteBatchIsSuccess() throws SQLException {
        final String INSERT_SQL = "insert into demo.teacher(id, name, joining_date)"
                + "values(?, ?, ?)";

        try(Connection connection = getConnection()) {
            PreparedStatement preparedStatement = connection.prepareStatement(INSERT_SQL);
            preparedStatement.setInt(1, 1);
            preparedStatement.setString(2, "Bret Lee");
            preparedStatement.setString(3, "07-08-2002");
            preparedStatement.addBatch();

            preparedStatement.setInt(1, 2);
            preparedStatement.setString(2, "Sarah Glimmer");
            preparedStatement.setString(3, "07-08-1997");
            preparedStatement.addBatch();

            assertDoesNotThrow(() -> preparedStatement.executeBatch());
        }
    }

    @Test
    void whenExecuteStoredToCreateTable_thenSuccess() throws SQLException {
        final String CREATE_TABLE_SQL = "CreateTable";
        try(Connection connection = getConnection()) {
            CallableStatement callableStatement = connection.prepareCall(CREATE_TABLE_SQL);

            callableStatement.setString("SchemaName", "prod"); //schema gets created too
            callableStatement.setString("TableName", "subject");
            callableStatement.setString("PrimaryKey", "id");
            Boolean result = callableStatement.execute();

            ResultSet resultSet = callableStatement.getResultSet();

            while(resultSet.next()) {
                String tableCreated = resultSet.getString("Success");
                assertEquals("true", tableCreated);
                logger.info("result of the callable execute:" + tableCreated);
            }
        }
    }
}
