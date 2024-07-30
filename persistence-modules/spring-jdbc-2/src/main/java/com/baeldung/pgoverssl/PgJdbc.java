package com.baeldung.pgoverssl;

import java.nio.file.Paths;
import java.sql.Connection;
import java.sql.DriverManager;
import java.sql.SQLException;
import java.util.Map;
import java.util.Properties;

public class PgJdbc {

    public void checkConnectionSsl(String url, String username, String password, Map<String, String> extraProps) {
        // set up connection properties
        Properties props = new Properties();
        props.putAll(extraProps);
        props.put("username", username);
        props.put("password", password);
        props.put("sslmode", "verify-ca");
        props.put("ssl", "true");

        try (Connection connection = DriverManager.getConnection(url, props)) {
            if (!connection.isClosed()) {
                connection.close();
            }
            // we had a successful connection
            System.out.println("Connection was successful");
        } catch (SQLException e) {
            System.out.println("Connection failed");
        }
    }

    public static void main(String[] args) {
        PgJdbc pg = new PgJdbc();
        String url = "jdbc:postgresql://localhost:5432/testdb";
        String username = "postgres";
        String password = "password";
        // base path for files
        String BASE_PATH = Paths.get(PgJdbc.class.getResource("/pgoverssl/certs").getPath())
            .toAbsolutePath()
            .toString();

        Map<String, String> connectionProperties = Map.of("sslcert", BASE_PATH.concat("/pg_client.crt"), "sslkey", BASE_PATH.concat("/pg_client.pk8"),
            "sslrootcert", BASE_PATH.concat("/root.crt"));

        // connection with regular certificate and private key properties
        System.out.println("Connection without keystore and truststore");
        pg.checkConnectionSsl(url, username, password, connectionProperties);

        //connection using keystore and truststore
        System.setProperty("javax.net.ssl.keyStore", BASE_PATH.concat("/pg_client.jks"));
        System.setProperty("javax.net.ssl.keyStorePassword", "password");
        System.setProperty("javax.net.ssl.trustStore", BASE_PATH.concat("/truststore.jks"));
        System.setProperty("javax.net.ssl.trustStorePassword", "password");

        // connection using trust store
        System.out.println("\nConnection using keystore and truststore");
        pg.checkConnectionSsl(url, username, password, Map.of("sslfactory", "org.postgresql.ssl.DefaultJavaSSLFactory"));
    }
}
