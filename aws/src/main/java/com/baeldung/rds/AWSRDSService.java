package com.baeldung.rds;

import com.amazonaws.auth.AWSCredentialsProvider;
import com.amazonaws.auth.AWSStaticCredentialsProvider;
import com.amazonaws.auth.BasicAWSCredentials;
import com.amazonaws.regions.Regions;
import com.amazonaws.services.rds.AmazonRDS;
import com.amazonaws.services.rds.AmazonRDSClientBuilder;
import com.amazonaws.services.rds.model.*;

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

    public AWSRDSService() {
        //Init RDS client with credentials and region.
        credentials = new
                AWSStaticCredentialsProvider(new
                BasicAWSCredentials("<ACCESS_KEY>",
                "SECRET_KEY"));
        amazonRDS = AmazonRDSClientBuilder.standard().withCredentials(credentials)
                .withRegion(Regions.AP_SOUTHEAST_2).build();
    }

    //create and launch new RDS instance
    public String launchInstance() {
        try {
            CreateDBInstanceRequest request = new CreateDBInstanceRequest();
            // RDS instance name
            request.setDBInstanceIdentifier("Sydney");
            request.setEngine("postgres");
            request.setMultiAZ(false);
            request.setMasterUsername("username");
            request.setMasterUserPassword("password");
            request.setDBName("mydb");
            request.setStorageType("gp2");
            request.setAllocatedStorage(10);

            DBInstance instance = amazonRDS.createDBInstance(request);

            // Information about the new RDS instance
            String identifier = instance.getDBInstanceIdentifier();
            String status = instance.getDBInstanceStatus();
            Endpoint endpoint = instance.getEndpoint();
            String endpoint_url = "Endpoint URL not available yet.";
            if (endpoint != null) {
                endpoint_url = endpoint.toString();
            }
            logger.info(identifier + "\t" + status);
            logger.info(endpoint_url);

            return identifier;
        } catch (Exception e) {
            e.printStackTrace();
            return "ERROR";
        }
    }

    // Describe DB instances
    public void listInstances() {
        try {
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
        } catch (Exception e) {
            e.printStackTrace();
        }
    }

    //Delete RDS instance
    public void terminateInstance(String identifier) {
        try {
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
        } catch (Exception e) {
            e.printStackTrace();
        }
    }


    public void runJdbcTests() {
        try {
            // Getting database properties from db.properties
            Properties prop = new Properties();
            InputStream input = AWSRDSService.class.getClassLoader().getResourceAsStream("db.properties");
            prop.load(input);
            String db_hostname = prop.getProperty("db_hostname");
            String db_username = prop.getProperty("db_username");
            String db_password = prop.getProperty("db_password");
            String db_database = prop.getProperty("db_database");
            String jdbc_url = "jdbc:postgresql://" + db_hostname + ":5432/" + db_database;

            Connection conn = null;
            try {
                // Create a connection using the JDBC driver
                conn = DriverManager.getConnection(jdbc_url, db_username, db_password);

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
                // Close the connection
                conn.close();

            } catch (Exception e1) {
                e1.printStackTrace();
            }
        } catch (Exception e0) {
            e0.printStackTrace();
        }
    }

    public static void main (String[] args){
        AWSRDSService awsrdsService = new AWSRDSService();

        String instanceName = awsrdsService.launchInstance();
        //Add some wait for instance creation.
        awsrdsService.listInstances();

        awsrdsService.runJdbcTests();

        awsrdsService.terminateInstance(instanceName);
    }
}
