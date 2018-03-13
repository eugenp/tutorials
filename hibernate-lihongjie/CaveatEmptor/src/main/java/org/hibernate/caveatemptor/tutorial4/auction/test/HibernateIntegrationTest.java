package org.hibernate.caveatemptor.tutorial4.auction.test;

import org.hibernate.impl.SessionFactoryImpl;
import auction.persistence.HibernateUtil;
import org.testng.annotations.*;
import org.dbunit.dataset.*;
import org.dbunit.dataset.xml.FlatXmlDataSet;
import org.dbunit.operation.DatabaseOperation;
import org.dbunit.database.*;

import java.io.*;
import java.util.*;
import java.sql.Connection;
import java.net.MalformedURLException;

public abstract class HibernateIntegrationTest {

    protected String dataSetLocation;
    protected List<DatabaseOperation> beforeTestOperations
            = new ArrayList<DatabaseOperation>();
    protected List<DatabaseOperation> afterTestOperations
            = new ArrayList<DatabaseOperation>();

    private ReplacementDataSet dataSet;

    @BeforeClass(groups = "integration-hibernate")
    public void prepareDataSet() throws Exception {

        // Check if subclass has prepared everything
        prepareSettings();
        if (dataSetLocation == null)
            throw new RuntimeException(
                "Test subclass needs to prepare a dataset location"
            );

        // Load the base dataset file
        try {

            InputStream input =
                    Thread.currentThread().getContextClassLoader()
                            .getResourceAsStream(dataSetLocation);

            dataSet = new ReplacementDataSet(
                            new FlatXmlDataSet(input)
                          );
            dataSet.addReplacementObject("[NULL]", null);

        } catch (DataSetException ex) {
            throw new RuntimeException(
                    "Couldn't load DBUnit dataset: " + dataSetLocation, ex
            );
        }
    }

    @BeforeMethod(groups = "integration-hibernate")
    public void beforeTestMethod() throws Exception {
        prepareSettings();
        for (DatabaseOperation op : beforeTestOperations ) {
            op.execute(getConnection(), dataSet);
        }
    }

    @AfterMethod(groups = "integration-hibernate")
    public void afterTestMethod() throws Exception {
        for (DatabaseOperation op : afterTestOperations ) {
            op.execute(getConnection(), dataSet);
        }
    }

    // Subclasses can/have to override the following methods

    protected IDatabaseConnection getConnection() throws Exception {

        // Get a JDBC connection from Hibernate
        Connection con =
            ((SessionFactoryImpl)HibernateUtil.getSessionFactory()).getSettings()
                    .getConnectionProvider().getConnection();

        // Disable foreign key constraint checking
        // This really depends on the DBMS product... here for HSQL DB
        con.prepareStatement("set referential_integrity FALSE")
            .execute();

        IDatabaseConnection dbUnitCon =
            new DatabaseConnection( con);

        // TODO: Remove this once DBUnit works with the latest HSQL DB
        DatabaseConfig config = dbUnitCon.getConfig();
        config.setProperty(DatabaseConfig.PROPERTY_DATATYPE_FACTORY, new FixDBUnit());

        return dbUnitCon;
    }

    protected abstract void prepareSettings();

}
