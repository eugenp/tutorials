package com.baeldung.dbunit;

import org.dbunit.Assertion;
import org.dbunit.IDatabaseTester;
import org.dbunit.JdbcDatabaseTester;
import org.dbunit.dataset.IDataSet;
import org.dbunit.dataset.ITable;
import org.dbunit.dataset.filter.DefaultColumnFilter;
import org.dbunit.dataset.xml.FlatXmlDataSetBuilder;
import org.dbunit.operation.DatabaseOperation;
import org.junit.After;
import org.junit.Before;
import org.junit.BeforeClass;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.junit.runners.JUnit4;

import java.io.InputStream;
import java.sql.Connection;
import java.sql.ResultSet;

import static com.baeldung.dbunit.ConnectionSettings.JDBC_DRIVER;
import static com.baeldung.dbunit.ConnectionSettings.JDBC_URL;
import static com.baeldung.dbunit.ConnectionSettings.PASSWORD;
import static com.baeldung.dbunit.ConnectionSettings.USER;
import static org.assertj.core.api.Assertions.assertThat;
import static org.dbunit.Assertion.assertEquals;

@RunWith(JUnit4.class)
public class OldSchoolDbUnitTest {

    private static IDatabaseTester tester = null;

    private Connection connection;

    @BeforeClass
    public static void setUp() throws Exception {
        tester = initDatabaseTester();
    }

    private static IDatabaseTester initDatabaseTester() throws Exception {
        JdbcDatabaseTester tester = new JdbcDatabaseTester(JDBC_DRIVER, JDBC_URL, USER, PASSWORD);
        tester.setDataSet(initDataSet());
        tester.setSetUpOperation(DatabaseOperation.REFRESH);
        tester.setTearDownOperation(DatabaseOperation.DELETE_ALL);
        return tester;
    }

    private static IDataSet initDataSet() throws Exception {
        try (InputStream is = OldSchoolDbUnitTest.class.getClassLoader().getResourceAsStream("dbunit/data.xml")) {
            return new FlatXmlDataSetBuilder().build(is);
        }
    }

    @Before
    public void setup() throws Exception {
        tester.onSetup();
        connection = tester.getConnection().getConnection();
    }

    @After
    public void tearDown() throws Exception {
        tester.onTearDown();
    }

    @Test
    public void givenDataSet_whenSelect_thenFirstTitleIsGreyTShirt() throws Exception {
        ResultSet rs = connection.createStatement().executeQuery("select * from ITEMS where id = 1");

        assertThat(rs.next()).isTrue();
        assertThat(rs.getString("title")).isEqualTo("Grey T-Shirt");
    }

    @Test
    public void givenDataSet_whenInsert_thenGetResultsAreStillEqualIfIgnoringColumnsWithDifferentProduced()
        throws Exception {
        String[] excludedColumns = { "id", "produced" };
        try (InputStream is = getClass().getClassLoader()
            .getResourceAsStream("dbunit/expected-ignoring-registered_at.xml")) {
            // given
            IDataSet expectedDataSet = new FlatXmlDataSetBuilder().build(is);
            ITable expectedTable = DefaultColumnFilter
                .excludedColumnsTable(expectedDataSet.getTable("ITEMS"), excludedColumns);

            // when
            connection.createStatement()
                .executeUpdate("INSERT INTO ITEMS (title, price, produced)  VALUES('Necklace', 199.99, now())");

            // then
            IDataSet databaseDataSet = tester.getConnection().createDataSet();
            ITable actualTable = DefaultColumnFilter
                .excludedColumnsTable(databaseDataSet.getTable("ITEMS"), excludedColumns);
            Assertion.assertEquals(expectedTable, actualTable);
        }
    }

    @Test
    public void givenDataSet_whenDelete_thenItemIsRemoved() throws Exception {
        try (InputStream is = OldSchoolDbUnitTest.class.getClassLoader()
            .getResourceAsStream("dbunit/items_exp_delete.xml")) {
            // given
            ITable expectedTable = new FlatXmlDataSetBuilder().build(is).getTable("ITEMS");

            // when
            connection.createStatement().executeUpdate("delete from ITEMS where id = 2");

            // then
            IDataSet databaseDataSet = tester.getConnection().createDataSet();
            ITable actualTable = databaseDataSet.getTable("ITEMS");
            assertEquals(expectedTable, actualTable);
        }
    }

    @Test
    public void givenDataSet_whenProductIgnoredAndDelete_thenItemIsRemoved() throws Exception {
        try (InputStream is = OldSchoolDbUnitTest.class.getClassLoader()
            .getResourceAsStream("dbunit/items_exp_delete_no_produced.xml")) {
            // given
            ITable expectedTable = new FlatXmlDataSetBuilder().build(is).getTable("ITEMS");

            // when
            connection.createStatement().executeUpdate("delete from ITEMS where id = 2");

            // then
            IDataSet databaseDataSet = tester.getConnection().createDataSet();
            ITable actualTable = databaseDataSet.getTable("ITEMS");
            actualTable = DefaultColumnFilter.excludedColumnsTable(actualTable, new String[] { "produced" });
            assertEquals(expectedTable, actualTable);
        }
    }

    @Test
    public void givenDataSet_whenUpdate_thenItemHasNewName() throws Exception {
        try (InputStream is = OldSchoolDbUnitTest.class.getClassLoader()
            .getResourceAsStream("dbunit/items_exp_rename.xml")) {
            // given
            ITable expectedTable = new FlatXmlDataSetBuilder().build(is).getTable("ITEMS");

            // when
            connection.createStatement().executeUpdate("update ITEMS set title='new name' where id = 1");

            // then
            IDataSet databaseDataSet = tester.getConnection().createDataSet();
            ITable actualTable = databaseDataSet.getTable("ITEMS");
            assertEquals(expectedTable, actualTable);
        }
    }

    @Test
    public void givenDataSet_whenUpdateWithNoProduced_thenItemHasNewName() throws Exception {
        try (InputStream is = OldSchoolDbUnitTest.class.getClassLoader()
            .getResourceAsStream("dbunit/items_exp_rename_no_produced.xml")) {
            // given
            ITable expectedTable = new FlatXmlDataSetBuilder().build(is).getTable("ITEMS");
            expectedTable = DefaultColumnFilter.excludedColumnsTable(expectedTable, new String[] { "produced" });

            // when
            connection.createStatement().executeUpdate("update ITEMS set title='new name' where id = 1");

            // then
            IDataSet databaseDataSet = tester.getConnection().createDataSet();
            ITable actualTable = databaseDataSet.getTable("ITEMS");
            actualTable = DefaultColumnFilter.excludedColumnsTable(actualTable, new String[] { "produced" });
            assertEquals(expectedTable, actualTable);
        }
    }

}
