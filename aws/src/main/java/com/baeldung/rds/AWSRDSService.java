package com.baeldung.rds;

import com.amazonaws.auth.AWSCredentialsProvider;
import com.amazonaws.auth.AWSStaticCredentialsProvider;
import com.amazonaws.auth.BasicAWSCredentials;
import com.amazonaws.regions.Regions;
import com.amazonaws.services.rds.AmazonRDS;
import com.amazonaws.services.rds.AmazonRDSClientBuilder;
import com.amazonaws.services.rds.model.*;

import java.io.IOException;
import java.io.InputStream;
import java.sql.*;
import java.util.List;
import java.util.Properties;
import java.util.UUID;
import java.util.logging.Logger;

public class AWSRDSService {


    final static Logger logger = Logger.getLogger(AWSRDSService.class.getName());
    private AWSCredentialsProvider credentials;
    private AmazonRDS amazonRDS;
    private String db_username;
    private String db_password;
    private String db_database;
    private String db_hostname;

    /*
    * User access key and secret key must be set before execute any operation.
    * Follow the link on how to get the user access and secret key
    * https://aws.amazon.com/blogs/security/wheres-my-secret-access-key/
    * **/
    public AWSRDSService() throws IOException {
        //Init RDS client with credentials and region.
        credentials = new
                AWSStaticCredentialsProvider(new
                BasicAWSCredentials("<ACCESS_KEY>",
                "<SECRET_KEY>"));
        amazonRDS = AmazonRDSClientBuilder.standard().withCredentials(credentials)
                .withRegion(Regions.AP_SOUTHEAST_2).build();
        Properties prop = new Properties();
        InputStream input = AWSRDSService.class.getClassLoader().getResourceAsStream("db.properties");
        prop.load(input);
        db_username = prop.getProperty("db_username");
        db_password = prop.getProperty("db_password");
        db_database = prop.getProperty("db_database");
    }

    public AWSRDSService(AmazonRDS amazonRDS){
        this.amazonRDS = amazonRDS;
    }

    /**
     * create the RDS instance.
     * After instance creation, update the db_hostname with endpoint in db.properties.
     * */

    public String launchInstance() {

        String identifier = "";
        CreateDBInstanceRequest request = new CreateDBInstanceRequest();
        // RDS instance name
        request.setDBInstanceIdentifier("Sydney");
        request.setEngine("postgres");
        request.setMultiAZ(false);
        request.setMasterUsername(db_username);
        request.setMasterUserPassword(db_password);
        request.setDBName(db_database);
        request.setStorageType("gp2");
        request.setAllocatedStorage(10);

        DBInstance instance = amazonRDS.createDBInstance(request);

        // Information about the new RDS instance
        identifier = instance.getDBInstanceIdentifier();
        String status = instance.getDBInstanceStatus();
        Endpoint endpoint = instance.getEndpoint();
        String endpoint_url = "Endpoint URL not available yet.";
        if (endpoint != null) {
            endpoint_url = endpoint.toString();
        }
        logger.info(identifier + "\t" + status);
        logger.info(endpoint_url);

        return identifier;

    }

    // Describe DB instances
    public void listInstances() {
        DescribeDBInstancesResult result = amazonRDS.describeDBInstances();
        List<DBInstance> instances = result.getDBInstances();
        for (DBInstance instance : instances) {
            // Information about each RDS instance
            String identifier = instance.getDBInstanceIdentifier();
            String engine = instance.getEngine();
            String status = instance.getDBInstanceStatus();
            Endpoint endpoint = instance.getEndpoint();
            String endpoint_url = "Endpoint URL not available yet.";
            if (endpoint != null) {
                endpoint_url = endpoint.toString();
            }
            logger.info(identifier + "\t" + engine + "\t" + status);
            logger.info("\t" + endpoint_url);
        }

    }

    //Delete RDS instance
    public void terminateInstance(String identifier) {

        DeleteDBInstanceRequest request = new DeleteDBInstanceRequest();
        request.setDBInstanceIdentifier(identifier);
        request.setSkipFinalSnapshot(true);

        // Delete the RDS instance
        DBInstance instance = amazonRDS.deleteDBInstance(request);

        // Information about the RDS instance being deleted
        String status = instance.getDBInstanceStatus();
        Endpoint endpoint = instance.getEndpoint();
        String endpoint_url = "Endpoint URL not available yet.";
        if (endpoint != null) {
            endpoint_url = endpoint.toString();
        }

        logger.info(identifier + "\t" + status);
        logger.info(endpoint_url);

    }


    public void runJdbcTests() throws SQLException, IOException {

        // Getting database properties from db.properties
        Properties prop = new Properties();
        InputStream input = AWSRDSService.class.getClassLoader().getResourceAsStream("db.properties");
        prop.load(input);
        db_hostname = prop.getProperty("db_hostname");
        String jdbc_url = "jdbc:postgresql://" + db_hostname + ":5432/" + db_database;

        // Create a connection using the JDBC driver
        try (Connection conn = DriverManager.getConnection(jdbc_url, db_username, db_password)) {

            // Create the test table if not exists
            Statement statement = conn.createStatement();
            String sql = "CREATE TABLE IF NOT EXISTS jdbc_test (id SERIAL PRIMARY KEY, content VARCHAR(80))";
            statement.executeUpdate(sql);

            // Do some INSERT
            PreparedStatement preparedStatement = conn.prepareStatement("INSERT INTO jdbc_test (content) VALUES (?)");
            String content = "" + UUID.randomUUID();
            preparedStatement.setString(1, content);
            preparedStatement.executeUpdate();
            logger.info("INSERT: " + content);

            // Do some SELECT
            sql = "SELECT  * FROM jdbc_test";
            ResultSet resultSet = statement.executeQuery(sql);
            while (resultSet.next()) {
                String count = resultSet.getString("content");
                logger.info("Total Records: " + count);
            }
        }


    }

    public static void main(String[] args) throws IOException, SQLException {
        AWSRDSService awsrdsService = new AWSRDSService();

        String instanceName = awsrdsService.launchInstance();

        //Add some wait for instance creation.

        awsrdsService.listInstances();

        awsrdsService.runJdbcTests();

        awsrdsService.terminateInstance(instanceName);
    }
}
