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

import static com.baeldung.dbunit.ConnectionSettings.JDBC_DRIVER;
import static com.baeldung.dbunit.ConnectionSettings.JDBC_URL;
import static com.baeldung.dbunit.ConnectionSettings.PASSWORD;
import static com.baeldung.dbunit.ConnectionSettings.USER;
import static org.assertj.core.api.Assertions.assertThat;

public class PrepAndExpectedDbUnitTest extends DefaultPrepAndExpectedTestCase {

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
        final Connection connection = getConnection().getConnection();
        final VerifyTableDefinition[] verifyTables = {new VerifyTableDefinition("CLIENTS", new String[]{})};
        final String[] prepDataFiles = {"/users.xml"};
        final String[] expectedDataFiles = {"/users.xml"};
        final PrepAndExpectedTestCaseSteps testSteps =
                () -> connection
                        .createStatement()
                        .executeQuery("select * from CLIENTS where id = 1");

        final ResultSet rs = (ResultSet) super.runTest(verifyTables, prepDataFiles, expectedDataFiles, testSteps);

        assertThat(rs.next()).isTrue();
        assertThat(rs.getString("last_name")).isEqualTo("Xavier");
    }

    @Test
    public void testUpdate() throws Exception {
        final Connection connection = getConnection().getConnection();
        final VerifyTableDefinition[] verifyTables = {new VerifyTableDefinition("CLIENTS", new String[]{})}; // define tables to verify
        final String[] prepDataFiles = {"/users.xml"};
        final String[] expectedDataFiles = {"/users_exp_rename.xml"};
        final PrepAndExpectedTestCaseSteps testSteps =
                () -> connection
                        .createStatement()
                        .executeUpdate("update CLIENTS set first_name = 'new name' where id = 1");

        super.runTest(verifyTables, prepDataFiles, expectedDataFiles, testSteps);
    }

    @Test
    public void testDelete() throws Exception {
        final Connection connection = getConnection().getConnection();
        final VerifyTableDefinition[] verifyTables = {new VerifyTableDefinition("CLIENTS", new String[]{})};
        final String[] prepDataFiles = {"/users.xml"};
        final String[] expectedDataFiles = {"/users_exp_delete.xml"};
        final PrepAndExpectedTestCaseSteps testSteps =
                () -> connection
                        .createStatement()
                        .executeUpdate("delete from CLIENTS where id = 2");

        super.runTest(verifyTables, prepDataFiles, expectedDataFiles, testSteps);
    }

}
