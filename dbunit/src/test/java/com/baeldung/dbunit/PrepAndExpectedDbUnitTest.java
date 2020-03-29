package com.baeldung.dbunit;

import org.dbunit.DefaultPrepAndExpectedTestCase;
import org.dbunit.IDatabaseTester;
import org.dbunit.JdbcDatabaseTester;
import org.dbunit.PrepAndExpectedTestCaseSteps;
import org.dbunit.VerifyTableDefinition;
import org.dbunit.dataset.IDataSet;
import org.dbunit.dataset.xml.FlatXmlDataSetBuilder;
import org.dbunit.operation.DatabaseOperation;
import org.dbunit.util.fileloader.DataFileLoader;
import org.dbunit.util.fileloader.FlatXmlDataFileLoader;
import org.junit.Test;

import java.io.InputStream;
import java.sql.Connection;
import java.sql.ResultSet;

import static org.assertj.core.api.Assertions.assertThat;

public class PrepAndExpectedDbUnitTest extends DefaultPrepAndExpectedTestCase {
    private static final String JDBC_DRIVER = org.h2.Driver.class.getName();
    private static final String JDBC_URL = "jdbc:h2:mem:default;DB_CLOSE_DELAY=-1;init=runscript from 'classpath:schema.sql'";
    private static final String USER = "sa";
    private static final String PASSWORD = "";

    @Override
    public void setUp() throws Exception {
        setDatabaseTester(initDatabaseTester());
        setDataFileLoader(initDataFileLoader());
        super.setUp();
    }

    private IDatabaseTester initDatabaseTester() throws Exception {
        final JdbcDatabaseTester tester = new JdbcDatabaseTester(JDBC_DRIVER, JDBC_URL, USER, PASSWORD);
        tester.setDataSet(initDataSet());
        tester.setSetUpOperation(DatabaseOperation.REFRESH);
        return tester;
    }

    private IDataSet initDataSet() throws Exception {
        final InputStream is = getClass().getClassLoader().getResourceAsStream("data.xml");
        return new FlatXmlDataSetBuilder().build(is);
    }

    private DataFileLoader initDataFileLoader() {
        return new FlatXmlDataFileLoader();
    }

    @Test
    public void testSelect() throws Exception {
        // Arrange
        final Connection connection = getConnection().getConnection();
        final VerifyTableDefinition[] verifyTables = {new VerifyTableDefinition("USERS", new String[]{})};
        final String[] prepDataFiles = {"/users.xml"};
        final String[] expectedDataFiles = {"/users.xml"};
        final PrepAndExpectedTestCaseSteps testSteps = () -> {
            // invoke the method being tested here; for the sake of simplicity we use JDBC API directly in this example
            final ResultSet rs = connection.createStatement().executeQuery("select * from USERS where id = 1");

            // either place assertions here
            //assertTrue(rs.next());
            //assertEquals("Xavier", rs.getString("last_name"));

            return rs;
        };

        // Act
        final ResultSet rs = (ResultSet) super.runTest(verifyTables, prepDataFiles, expectedDataFiles, testSteps);

        // or place assertions at the end
        assertThat(rs.next()).isTrue();
        assertThat(rs.getString("last_name")).isEqualTo("Xavier");
    }

    @Test
    public void testUpdate() throws Exception {
        // Arrange
        final Connection connection = getConnection().getConnection();
        final VerifyTableDefinition[] verifyTables = {new VerifyTableDefinition("USERS", new String[]{})}; // define tables to verify
        final String[] prepDataFiles = {"/users.xml"}; // define prep files
        final String[] expectedDataFiles = {"/users_exp_rename.xml"}; // define expected files
        final PrepAndExpectedTestCaseSteps testSteps = () -> {
            // invoke the method being tested here; for the sake of simplicity we use JDBC API directly in this example
            return connection.createStatement().executeUpdate("update USERS set first_name = 'new name' where id = 1");
            // after this method exits, dbUnit will:
            //  * verify configured tables
            //  * cleanup tables as configured
        };

        // Act
        super.runTest(verifyTables, prepDataFiles, expectedDataFiles, testSteps);
    }

    @Test
    public void testDelete() throws Exception {
        // Arrange
        final Connection connection = getConnection().getConnection();
        final VerifyTableDefinition[] verifyTables = {new VerifyTableDefinition("USERS", new String[]{})};
        final String[] prepDataFiles = {"/users.xml"};
        final String[] expectedDataFiles = {"/users_exp_delete.xml"};
        final PrepAndExpectedTestCaseSteps testSteps =
                () -> connection.createStatement().executeUpdate("delete from USERS where id = 2");

        // Act
        super.runTest(verifyTables, prepDataFiles, expectedDataFiles, testSteps);
    }

}
