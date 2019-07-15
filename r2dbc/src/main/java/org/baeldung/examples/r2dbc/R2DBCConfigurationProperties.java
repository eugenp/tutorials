package org.baeldung.examples.r2dbc;

import java.time.Duration;
import java.util.Collections;
import java.util.Map;

import javax.validation.constraints.NotEmpty;

import org.springframework.boot.context.properties.ConfigurationProperties;

@ConfigurationProperties( prefix = "r2dbc")
public class R2DBCConfigurationProperties {

    @NotEmpty
    private String driver;
    
    private String database;
    private String protocol;
    private String host;
    private int port;    
    private String user;
    private String password;
    private boolean ssl = false;
    private Map<String,Object> options = Collections.emptyMap();
    private Duration connectTimeout = Duration.ofSeconds(10);
    /**
     * @return the driver
     */
    public String getDriver() {
        return driver;
    }
    /**
     * @param driver the driver to set
     */
    public void setDriver(String driver) {
        this.driver = driver;
    }
    /**
     * @return the database
     */
    public String getDatabase() {
        return database;
    }
    /**
     * @param database the database to set
     */
    public void setDatabase(String database) {
        this.database = database;
    }
    /**
     * @return the protocol
     */
    public String getProtocol() {
        return protocol;
    }
    /**
     * @param protocol the protocol to set
     */
    public void setProtocol(String protocol) {
        this.protocol = protocol;
    }

    /**
     * @return the host
     */
    public String getHost() {
        return host;
    }
    /**
     * @param host the host to set
     */
    public void setHost(String host) {
        this.host = host;
    }
    /**
     * @return the port
     */
    public int getPort() {
        return port;
    }
    /**
     * @param port the port to set
     */
    public void setPort(int port) {
        this.port = port;
    }
    /**
     * @return the user
     */
    public String getUser() {
        return user;
    }
    /**
     * @param user the user to set
     */
    public void setUser(String user) {
        this.user = user;
    }
    /**
     * @return the password
     */
    public String getPassword() {
        return password;
    }
    /**
     * @param password the password to set
     */
    public void setPassword(String password) {
        this.password = password;
    }
    /**
     * @return the ssl
     */
    public boolean isSsl() {
        return ssl;
    }
    /**
     * @param ssl the ssl to set
     */
    public void setSsl(boolean ssl) {
        this.ssl = ssl;
    }
    /**
     * @return the options
     */
    public Map<String, Object> getOptions() {
        return options;
    }
    /**
     * @param options the options to set
     */
    public void setOptions(Map<String, Object> options) {
        this.options = options;
    }
    /**
     * @return the connectTimeout
     */
    public Duration getConnectTimeout() {
        return connectTimeout;
    }
    /**
     * @param connectTimeout the connectTimeout to set
     */
    public void setConnectTimeout(Duration connectTimeout) {
        this.connectTimeout = connectTimeout;
    }
    
    
    
    

}
