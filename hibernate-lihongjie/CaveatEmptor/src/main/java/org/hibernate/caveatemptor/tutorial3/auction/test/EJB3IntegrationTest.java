package org.hibernate.caveatemptor.tutorial3.auction.test;

import java.util.List;
import java.util.ArrayList;
import java.io.InputStream;
import java.io.File;
import java.sql.Connection;

import org.dbunit.dataset.*;
import org.dbunit.dataset.xml.FlatXmlDataSet;
import org.dbunit.operation.DatabaseOperation;
import org.dbunit.database.*;
import org.testng.annotations.*;
import org.jboss.ejb3.embedded.EJB3StandaloneBootstrap;

import javax.naming.*;
import javax.sql.DataSource;
import javax.transaction.UserTransaction;
import javax.persistence.EntityManagerFactory;

public abstract class EJB3IntegrationTest {

    // Configuration options
    private String JNDI_DATASOURCE;
    private String JNDI_NAME_EMF;
    private String JNDI_NAME_USERTX;

    protected InitialContext jndi;

    protected String dataSetLocation;
    protected List<DatabaseOperation> beforeTestOperations
            = new ArrayList<DatabaseOperation>();
    protected List<DatabaseOperation> afterTestOperations
            = new ArrayList<DatabaseOperation>();

    private ReplacementDataSet dataSet;

    @BeforeTest(groups = "integration-persistence")
    @Parameters({"deploy_beans_xml", "scan_classpath",
                 "jndi_datasource", "jndi_name_emf", "jndi_name_usertx"})
    public void startContainer(String deployBeansXml, String scanClasspath,
                               String jndiDatasource, String jndiNameEMF, String jndiNameUserTx)
            throws Exception {
        // Set configuration options from TestNG parameters
        JNDI_DATASOURCE = jndiDatasource;
        JNDI_NAME_EMF = jndiNameEMF;
        JNDI_NAME_USERTX = jndiNameUserTx;

        // Boot the JBoss Microcontainer with EJB3 settings, automatically
        // loads ejb3-interceptors-aop.xml and embedded-jboss-beans.xml
        EJB3StandaloneBootstrap.boot(null);

        // Deploy custom stateless beans (datasource, mostly)
        EJB3StandaloneBootstrap.deployXmlResource(deployBeansXml);

        // Deploy all EJBs found on classpath (slow, scans all)
        //EJB3StandaloneBootstrap.scanClasspath();

        // Deploy all EJBs found on classpath (fast, scans only build directory)
        // This is a relative location, matching the substring end of one of java.class.path locations!
        // Print out System.getProperty("java.class.path") to understand this...
        EJB3StandaloneBootstrap.scanClasspath(scanClasspath.replace("/", File.separator));

        // Create InitialContext from jndi.properties
        jndi = new InitialContext();
    }

    @BeforeClass(groups = "integration-persistence")
    public void prepareDataSet() throws Exception {

        // Check if subclass has prepared everything
        prepareSettings();
        if (dataSetLocation == null)
            throw new RuntimeException(
                "Test subclass needs to prepare a dataset location"
            );

        // Load the base dataset file
        InputStream input =
                Thread.currentThread().getContextClassLoader()
                        .getResourceAsStream(dataSetLocation);

        dataSet = new ReplacementDataSet(
                        new FlatXmlDataSet(input)
                      );
        dataSet.addReplacementObject("[NULL]", null);
    }

    @BeforeMethod(groups = "integration-persistence")
    public void beforeTestMethod() throws Exception {
        prepareSettings();
        for (DatabaseOperation op : beforeTestOperations ) {
            op.execute(getConnection(), dataSet);
        }
    }

    @AfterMethod(groups = "integration-persistence")
    public void afterTestMethod() throws Exception {
        for (DatabaseOperation op : afterTestOperations ) {
            op.execute(getConnection(), dataSet);
        }
    }

    // Convenience for subclasses

    protected UserTransaction getUserTransaction() throws Exception {
        return (UserTransaction)jndi.lookup(JNDI_NAME_USERTX);
    }

    protected EntityManagerFactory getEntityManagerFactory() throws Exception{
        return (EntityManagerFactory)jndi.lookup(JNDI_NAME_EMF);
    }

    @SuppressWarnings({"unchecked"})
    protected <T> T lookupLocalBean(Class<T> beanInterface, String beanImpl) {
        try {
            return (T)jndi.lookup(beanImpl + "/local");
        } catch (NamingException ex) {
            throw new RuntimeException(ex);
        }
    }

    // Subclasses can/have to override the following methods

    protected IDatabaseConnection getConnection() throws Exception {

        // Get a JDBC connection from JNDI datasource
        Connection con = ((DataSource) jndi.lookup(JNDI_DATASOURCE)).getConnection();

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

    /**
     * Override this in a subclass. It will be called before each test
     * method. Use it to stack DBUnit <tt>DatabaseOperation</tt>'s with
     * the <tt>beforeTestOperations</tt> and <tt>afterTestOperations</tt>
     * lists. You can also modify the <tt>afterTestOperations</tt> list
     * <i>inside</i> a test method, if you require additional clean up
     * once the method completes.
     */
    protected abstract void prepareSettings();

}
