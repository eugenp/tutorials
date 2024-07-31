package com.baeldung.dbunit;

import org.dbunit.Assertion;
import org.dbunit.DataSourceBasedDBTestCase;
import org.dbunit.assertion.DiffCollectingFailureHandler;
import org.dbunit.assertion.Difference;
import org.dbunit.dataset.IDataSet;
import org.dbunit.dataset.ITable;
import org.dbunit.dataset.xml.FlatXmlDataSetBuilder;
import org.dbunit.operation.DatabaseOperation;
import org.h2.jdbcx.JdbcDataSource;
import org.junit.After;
import org.junit.Before;
import org.junit.Test;
import org.junit.platform.commons.logging.Logger;
import org.junit.platform.commons.logging.LoggerFactory;
import org.junit.runner.RunWith;
import org.junit.runners.JUnit4;

import javax.sql.DataSource;
import java.io.InputStream;
import java.sql.Connection;
import java.sql.ResultSet;
import java.sql.SQLException;

import static com.baeldung.dbunit.ConnectionSettings.JDBC_URL;
import static com.baeldung.dbunit.ConnectionSettings.PASSWORD;
import static com.baeldung.dbunit.ConnectionSettings.USER;
import static java.util.stream.Collectors.joining;
import static org.assertj.core.api.Assertions.assertThat;
import static org.dbunit.Assertion.assertEqualsIgnoreCols;

@RunWith(JUnit4.class)
public class DataSourceDBUnitTest extends DataSourceBasedDBTestCase {

    private static final Logger logger = LoggerFactory.getLogger(DataSourceDBUnitTest.class);

    private Connection connection;

    @Override
    protected DataSource getDataSource() {
        JdbcDataSource dataSource = new JdbcDataSource();
        dataSource.setURL(JDBC_URL);
        dataSource.setUser(USER);
        dataSource.setPassword(PASSWORD);
        return dataSource;
    }

    @Override
    protected IDataSet getDataSet() throws Exception {
        try (InputStream resourceAsStream = getClass().getClassLoader().getResourceAsStream("dbunit/data.xml")) {
            return new FlatXmlDataSetBuilder().build(resourceAsStream);
        }
    }

    @Override
    protected DatabaseOperation getSetUpOperation() {
        return DatabaseOperation.REFRESH;
    }

    @Override
    protected DatabaseOperation getTearDownOperation() {
        return DatabaseOperation.DELETE_ALL;
    }

    @Before
    public void setUp() throws Exception {
        super.setUp();
        connection = getConnection().getConnection();
    }

    @After
    public void tearDown() throws Exception {
        super.tearDown();
    }

    @Test
    public void givenDataSet_whenSelect_thenFirstTitleIsGreyTShirt() throws SQLException {
        ResultSet rs = connection.createStatement().executeQuery("select * from ITEMS where id = 1");

        assertThat(rs.next()).isTrue();
        assertThat(rs.getString("title")).isEqualTo("Grey T-Shirt");
    }

    @Test
    public void givenDataSetEmptySchema_whenDataSetCreated_thenTablesAreEqual() throws Exception {
        IDataSet expectedDataSet = getDataSet();
        ITable expectedTable = expectedDataSet.getTable("CLIENTS");
        IDataSet databaseDataSet = getConnection().createDataSet();
        ITable actualTable = databaseDataSet.getTable("CLIENTS");
        Assertion.assertEquals(expectedTable, actualTable);
    }

    @Test
    public void givenDataSet_whenInsert_thenTableHasNewClient() throws Exception {
        try (InputStream is = getClass().getClassLoader().getResourceAsStream("dbunit/expected-user.xml")) {
            // given
            IDataSet expectedDataSet = new FlatXmlDataSetBuilder().build(is);
            ITable expectedTable = expectedDataSet.getTable("CLIENTS");
            Connection conn = getDataSource().getConnection();

            // when
            conn.createStatement()
                .executeUpdate("INSERT INTO CLIENTS (first_name, last_name) VALUES ('John', 'Jansen')");
            ITable actualData = getConnection()
                .createQueryTable("result_name", "SELECT * FROM CLIENTS WHERE last_name='Jansen'");

            // then
            assertEqualsIgnoreCols(expectedTable, actualData, new String[] { "id" });
        }
    }

    @Test
    public void givenDataSet_whenDelete_thenItemIsDeleted() throws Exception {
        try (InputStream is = DataSourceDBUnitTest.class.getClassLoader()
            .getResourceAsStream("dbunit/items_exp_delete.xml")) {
            // given
            ITable expectedTable = (new FlatXmlDataSetBuilder().build(is)).getTable("ITEMS");

            // when
            connection.createStatement().executeUpdate("delete from ITEMS where id = 2");

            // then
            IDataSet databaseDataSet = getConnection().createDataSet();
            ITable actualTable = databaseDataSet.getTable("ITEMS");
            Assertion.assertEquals(expectedTable, actualTable);
        }
    }

    @Test
    public void givenDataSet_whenUpdate_thenItemHasNewName() throws Exception {
        try (InputStream is = DataSourceDBUnitTest.class.getClassLoader()
            .getResourceAsStream("dbunit/items_exp_rename.xml")) {
            // given
            ITable expectedTable = (new FlatXmlDataSetBuilder().build(is)).getTable("ITEMS");

            connection.createStatement().executeUpdate("update ITEMS set title='new name' where id = 1");

            IDataSet databaseDataSet = getConnection().createDataSet();
            ITable actualTable = databaseDataSet.getTable("ITEMS");

            Assertion.assertEquals(expectedTable, actualTable);
        }
    }

    @Test
    public void givenDataSet_whenInsertUnexpectedData_thenFail() throws Exception {
        try (InputStream is = getClass().getClassLoader()
            .getResourceAsStream("dbunit/expected-multiple-failures.xml")) {

            // given
            IDataSet expectedDataSet = new FlatXmlDataSetBuilder().build(is);
            ITable expectedTable = expectedDataSet.getTable("ITEMS");
            Connection conn = getDataSource().getConnection();
            DiffCollectingFailureHandler collectingHandler = new DiffCollectingFailureHandler();

            // when
            conn.createStatement().executeUpdate("INSERT INTO ITEMS (title, price) VALUES ('Battery', '1000000')");
            ITable actualData = getConnection().createDataSet().getTable("ITEMS");

            // then
            Assertion.assertEquals(expectedTable, actualData, collectingHandler);
            if (!collectingHandler.getDiffList().isEmpty()) {
                String message = (String) collectingHandler.getDiffList().stream()
                    .map(d -> formatDifference((Difference) d)).collect(joining("\n"));
                logger.error(() -> message);
            }
        }
    }

    private static String formatDifference(Difference diff) {
        return "expected value in " + diff.getExpectedTable().getTableMetaData().getTableName() + "." + diff
            .getColumnName() + " row " + diff.getRowIndex() + ":" + diff.getExpectedValue() + ", but was: " + diff
            .getActualValue();
    }
}
