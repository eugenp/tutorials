package com.baeldung.hexagonalarchitecture.liquiditytracker.config;

/**
 * @author Víctor Gil
 *
 * since March 2019
 */
public class DatabaseConfigValues {

    private String driverName;
    private String hostname;
    private String databaseName;
    private String username;
    private String password;

    @Override
    public String toString() {
        return "DatabaseConfigValues [driverName=" + driverName + ", hostname=" + hostname + ", databaseName="
                + databaseName + ", username=" + username + ", password=" + password + "]";
    }

    // Just getters and setters
    public String getDriverName() {
        return driverName;
    }

    public void setDriverName(String driverName) {
        this.driverName = driverName;
    }

    public String getHostname() {
        return hostname;
    }

    public void setHostname(String hostname) {
        this.hostname = hostname;
    }

    public String getDatabaseName() {
        return databaseName;
    }

    public void setDatabaseName(String databaseName) {
        this.databaseName = databaseName;
    }

    public String getUsername() {
        return username;
    }

    public void setUsername(String username) {
        this.username = username;
    }

    public String getPassword() {
        return password;
    }

    public void setPassword(String password) {
        this.password = password;
    }
}
