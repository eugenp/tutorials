package com.baeldung.dbunit;

import static java.util.stream.Collectors.joining;

import java.sql.Connection;
import java.sql.SQLException;

import javax.sql.DataSource;

import org.dbunit.Assertion;
import org.dbunit.DataSourceBasedDBTestCase;
import org.dbunit.DefaultPrepAndExpectedTestCase;
import org.dbunit.PrepAndExpectedTestCase;
import org.dbunit.assertion.DiffCollectingFailureHandler;
import org.dbunit.assertion.Difference;
import org.dbunit.dataset.IDataSet;
import org.dbunit.dataset.ITable;
import org.dbunit.dataset.filter.DefaultColumnFilter;
import org.dbunit.dataset.xml.FlatXmlDataSetBuilder;
import org.h2.jdbcx.JdbcDataSource;

public class DBUnitTest extends DataSourceBasedDBTestCase {

    private PrepAndExpectedTestCase testCase = new DefaultPrepAndExpectedTestCase();

    @Override
    protected DataSource getDataSource() {
        JdbcDataSource dataSource = new JdbcDataSource();
        dataSource.setURL("jdbc:h2:mem:default;DB_CLOSE_DELAY=-1;init=runscript from 'classpath:schema.sql'");
        dataSource.setUser("sa");
        dataSource.setPassword("sa");
        return dataSource;
    }

    @Override protected IDataSet getDataSet() throws Exception {
        return new FlatXmlDataSetBuilder().build(getClass().getClassLoader().getResourceAsStream("data.xml"));
    }

    public void testEmptySchema() throws Exception {
        IDataSet expectedDataSet = new FlatXmlDataSetBuilder().build(getClass().getClassLoader().getResourceAsStream("data.xml"));
        ITable expectedTable = expectedDataSet.getTable("USER");

        IDataSet databaseDataSet = getConnection().createDataSet();
        ITable actualTable = databaseDataSet.getTable("USER");

        Assertion.assertEquals(expectedTable, actualTable);
    }

    public void testAssertByQuery() throws Exception {
        IDataSet expectedDataSet = new FlatXmlDataSetBuilder().build(getClass().getClassLoader().getResourceAsStream("expected-user1.xml"));
        ITable expectedTable = expectedDataSet.getTable("USER");

        Connection conn = getDataSource().getConnection();
        conn.createStatement().executeUpdate("INSERT INTO USER (email, registered_at) VALUES ('user1@example.org', '2017-08-05')");

        ITable actualData = getConnection().createQueryTable("result_name", "SELECT * FROM USER WHERE email='user1@example.org'");
        Assertion.assertEquals(expectedTable, actualData);
    }

    public void testIgnoringRegisteredAt() throws Exception {
        String[] excludedColumns = { "registered_at" };
        IDataSet expectedDataSet = new FlatXmlDataSetBuilder().build(getClass().getClassLoader()
                .getResourceAsStream("expected-ignoring-registered_at.xml"));
        ITable expectedTable = DefaultColumnFilter.excludedColumnsTable(expectedDataSet.getTable("USER"), excludedColumns);

        Connection conn = getDataSource().getConnection();
        conn.createStatement().executeUpdate("INSERT INTO USER (email, registered_at) VALUES ('user1@example.org', now())");

        IDataSet databaseDataSet = getConnection().createDataSet();
        ITable actualTable = DefaultColumnFilter.excludedColumnsTable(databaseDataSet.getTable("USER"), excludedColumns);

        Assertion.assertEquals(expectedTable, actualTable);
    }

    private static String formatDifference(Difference diff) {
        return "expected value in " + diff.getExpectedTable().getTableMetaData().getTableName()
                + "." + diff.getColumnName() + " row " + diff.getRowIndex() + ":"
                + diff.getExpectedValue() + ", but was: "
                + diff.getActualValue();
    }

    public void testMultipleFailures() throws Exception {
        IDataSet expectedDataSet = new FlatXmlDataSetBuilder().build(getClass().getClassLoader()
                .getResourceAsStream("expected-multiple-failures.xml"));
        ITable expectedTable = expectedDataSet.getTable("USER");

        Connection conn = getDataSource().getConnection();
        conn.createStatement().executeUpdate("INSERT INTO USER (email, registered_at) VALUES ('user1@example.org', '2017-08-05')");

        ITable actualData = getConnection().createDataSet().getTable("USER");

        DiffCollectingFailureHandler collectingHandler = new DiffCollectingFailureHandler();
        Assertion.assertEquals(expectedTable, actualData, collectingHandler);
        if (!collectingHandler.getDiffList().isEmpty()) {
            String message = (String) collectingHandler.getDiffList().stream()
                    .map(d -> formatDifference((Difference) d))
                    .collect(joining("\n"));
            throw new AssertionError(message);
        }
    }
}
