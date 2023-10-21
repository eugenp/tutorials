package com.baeldung.rds;

import java.io.IOException;
import java.io.InputStream;
import java.sql.*;
import java.util.List;
import java.util.Properties;
import java.util.UUID;
import java.util.logging.Logger;

import software.amazon.awssdk.auth.credentials.ProfileCredentialsProvider;
import software.amazon.awssdk.regions.Region;
import software.amazon.awssdk.services.rds.RdsClient;
import software.amazon.awssdk.services.rds.model.CreateDbInstanceRequest;
import software.amazon.awssdk.services.rds.model.CreateDbInstanceResponse;
import software.amazon.awssdk.services.rds.model.DBInstance;
import software.amazon.awssdk.services.rds.model.DeleteDbInstanceRequest;
import software.amazon.awssdk.services.rds.model.DeleteDbInstanceResponse;
import software.amazon.awssdk.services.rds.model.DescribeDbInstancesResponse;
import software.amazon.awssdk.services.rds.model.Endpoint;

public class AWSRDSService {


    final static Logger logger = Logger.getLogger(AWSRDSService.class.getName());
    private RdsClient rdsClient;
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
        Properties prop = new Properties();
        InputStream input = AWSRDSService.class.getClassLoader().getResourceAsStream("db.properties");
        prop.load(input);
        db_username = prop.getProperty("db_username");
        db_password = prop.getProperty("db_password");
        db_database = prop.getProperty("db_database");

        rdsClient = RdsClient.builder()
            .region(Region.AP_SOUTHEAST_2)
            .credentialsProvider(ProfileCredentialsProvider.create("default"))
            .build();
    }

    /**
     * create the RDS instance.
     * After instance creation, update the db_hostname with endpoint in db.properties.
     * */

    public String launchInstance() {

        String identifier = "";
        CreateDbInstanceRequest instanceRequest = CreateDbInstanceRequest.builder()
            .dbInstanceIdentifier("Sydney")
            .engine("postgres")
            .multiAZ(false)
            .masterUsername(db_username)
            .masterUserPassword(db_password)
            .dbName(db_database)
            .storageType("gp2")
            .allocatedStorage(10)
            .build();

        CreateDbInstanceResponse createDbInstanceResponse = rdsClient.createDBInstance(instanceRequest);

        // Information about the new RDS instance
        identifier = createDbInstanceResponse.dbInstance().dbInstanceIdentifier();
        String status = createDbInstanceResponse.dbInstance().dbInstanceStatus();
        Endpoint endpoint = createDbInstanceResponse.dbInstance().endpoint();
        String endpointUrl = "Endpoint URL not available yet.";
        if (endpoint != null) {
            endpointUrl = endpoint.toString();
        }
        logger.info(identifier + "\t" + status);
        logger.info(endpointUrl);

        return identifier;

    }

    // Describe DB instances
    public void listInstances() {
        DescribeDbInstancesResponse response = rdsClient.describeDBInstances();
        List<DBInstance> instances = response.dbInstances();
        for (DBInstance instance : instances) {
            // Information about each RDS instance
            String identifier = instance.dbInstanceIdentifier();
            String engine = instance.engine();
            String status = instance.dbInstanceStatus();
            Endpoint endpoint = instance.endpoint();
            String endpointUrl = "Endpoint URL not available yet.";
            if (endpoint != null) {
                endpointUrl = endpoint.toString();
            }
            logger.info(identifier + "\t" + engine + "\t" + status);
            logger.info("\t" + endpointUrl);
        }
    }

    //Delete RDS instance
    public void terminateInstance(String identifier) {

        DeleteDbInstanceRequest request = DeleteDbInstanceRequest.builder()
            .dbInstanceIdentifier(identifier)
            .skipFinalSnapshot(true)
            .build();

        // Delete the RDS instance
        DeleteDbInstanceResponse response = rdsClient.deleteDBInstance(request);

        // Information about the RDS instance being deleted
        String status = response.dbInstance().dbInstanceStatus();
        Endpoint endpoint = response.dbInstance().endpoint();
        String endpointUrl = "Endpoint URL not available yet.";
        if (endpoint != null) {
            endpointUrl = endpoint.toString();
        }

        logger.info(identifier + "\t" + status);
        logger.info(endpointUrl);

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
