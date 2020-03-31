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

import javax.sql.DataSource;
import java.sql.Connection;
import java.sql.ResultSet;
import java.sql.SQLException;

import static com.baeldung.dbunit.ConnectionSettings.JDBC_URL;
import static java.util.stream.Collectors.joining;
import static org.assertj.core.api.Assertions.assertThat;

public class DataSourceDBUnitTest extends DataSourceBasedDBTestCase {

    @Override
    protected DataSource getDataSource() {
        JdbcDataSource dataSource = new JdbcDataSource();
        dataSource.setURL(JDBC_URL);
        dataSource.setUser("sa");
        dataSource.setPassword("");
        return dataSource;
    }

    @Override
    protected IDataSet getDataSet() throws Exception {
        return new FlatXmlDataSetBuilder().build(getClass()
                .getClassLoader()
                .getResourceAsStream("data.xml"));
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
    }

    @After
    public void tearDown() throws Exception {
        super.tearDown();
    }


    @Test
    public void testSimpleDataSet() throws SQLException {
        final Connection connection = getDataSource()
                .getConnection();

        final ResultSet rs = connection
                .createStatement()
                .executeQuery("select * from iTEMS where id = 1");

        assertThat(rs.next()).isTrue();
        assertThat(rs.getString("title")).isEqualTo("Grey T-Shirt");
    }

    @Test
    public void testEmptySchema() throws Exception {
        IDataSet expectedDataSet = getDataSet();
        ITable expectedTable = expectedDataSet.getTable("CLIENTS");
        IDataSet databaseDataSet = getConnection().createDataSet();
        ITable actualTable = databaseDataSet.getTable("CLIENTS");
        Assertion.assertEquals(expectedTable, actualTable);
    }

    @Test
    public void testAssertByQuery() throws Exception {
        IDataSet expectedDataSet = new FlatXmlDataSetBuilder().build(getClass()
                .getClassLoader()
                .getResourceAsStream("expected-user.xml"));
        ITable expectedTable = expectedDataSet.getTable("CLIENTS");
        Connection conn = getDataSource().getConnection();
        conn.createStatement()
                .executeUpdate(
                        "INSERT INTO CLIENTS (first_name, last_name) VALUES ('John', 'Jansen')");
        ITable actualData = getConnection()
                .createQueryTable(
                        "result_name",
                        "SELECT * FROM CLIENTS WHERE last_name='Jansen'");
        Assertion.assertEqualsIgnoreCols(expectedTable, actualData, new String[]{"id"});
    }

    @Test(expected = AssertionError.class)
    public void testMultipleFailures() throws Exception {
        IDataSet expectedDataSet = new FlatXmlDataSetBuilder().build(getClass().getClassLoader().getResourceAsStream("expected-multiple-failures.xml"));
        ITable expectedTable = expectedDataSet.getTable("ITEMS");
        Connection conn = getDataSource().getConnection();
        conn.createStatement().executeUpdate("INSERT INTO ITEMS (title, price) VALUES ('Battery', '1000000')");
        ITable actualData = getConnection().createDataSet().getTable("ITEMS");
        DiffCollectingFailureHandler collectingHandler = new DiffCollectingFailureHandler();
        Assertion.assertEquals(expectedTable, actualData, collectingHandler);
        if (!collectingHandler.getDiffList().isEmpty()) {
            String message = (String) collectingHandler.getDiffList().stream().map(d -> formatDifference((Difference) d)).collect(joining("\n"));
//        throw new AssertionError(message);
        }
    }

    private static String formatDifference(Difference diff) {
        return "expected value in " + diff.getExpectedTable().getTableMetaData().getTableName() + "." + diff.getColumnName() + " row " + diff.getRowIndex() + ":" + diff.getExpectedValue() + ", but was: " + diff.getActualValue();
    }
}
