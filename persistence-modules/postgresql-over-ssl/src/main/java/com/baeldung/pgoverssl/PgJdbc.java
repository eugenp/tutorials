package com.baeldung.pgoverssl;

import java.sql.*;
import java.util.Properties;
import java.nio.file.*;

public class PgJdbc {

    private final Path PATH = Paths.get("certs");

    public boolean checkConnectionSslTrustStore() {
        // define properties
        Properties props = new Properties();
        props.setProperty("user", "postgres");
        props.setProperty("password", "password");
        props.setProperty("ssl", "true");
        props.setProperty("sslmode", "verify-ca");
        // keystore
        System.setProperty("javax.net.ssl.keyStore", PATH.toAbsolutePath() + "/pg_client.jks");
        System.setProperty("javax.net.ssl.keyStorePassword", "password");
        System.setProperty("javax.net.ssl.trustStore", PATH.toAbsolutePath()
            .toString() + "/truststore.jks");
        System.setProperty("javax.net.ssl.trustStorePassword", "password");
        // define url
        String url = "jdbc:postgresql://localhost:5432/testdb?sslmode=verify-ca&sslfactory=org.postgresql.ssl.DefaultJavaSSLFactory";
        // connect
        try (Connection connection = DriverManager.getConnection(url, props)) {
            // connected
            if (!connection.isClosed()) {
                connection.close();
            }
            // we have successfully tested our connection
            return true;
        } catch (SQLException e) {
            e.printStackTrace();
            return false;
        }
    }

    public boolean checkConnectionSsl() {
        // define properties
        Properties props = new Properties();
        props.setProperty("user", "postgres");
        props.setProperty("password", "password");
        props.setProperty("ssl", "true");
        props.setProperty("sslmode", "verify-ca");
        props.setProperty("sslcert", PATH.toAbsolutePath() + "/pg_client.crt");
        props.setProperty("sslrootcert", PATH.toAbsolutePath() + "/root.crt");
        props.setProperty("sslkey", PATH.toAbsolutePath() + "/pg_client.pk8");

        // define url
        String url = "jdbc:postgresql://localhost:5432/testdb?sslmode=verify-ca";
        // connect
        try (Connection connection = DriverManager.getConnection(url, props)) {
            // connected
            if (!connection.isClosed()) {
                connection.close();
            }
            // we have successfully tested our connection
            return true;
        } catch (SQLException e) {
            e.printStackTrace();
            return false;
        }
    }

    public static void main(String[] args) {
        PgJdbc pg = new PgJdbc();
        if (pg.checkConnectionSslTrustStore()) {
            System.out.println("Successful with trust store");
        } else {
            System.out.println("Failed to connect");
        }
    }
}
