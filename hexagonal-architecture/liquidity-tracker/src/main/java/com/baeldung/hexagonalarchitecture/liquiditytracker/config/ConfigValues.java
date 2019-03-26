package com.baeldung.hexagonalarchitecture.liquiditytracker.config;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import com.fasterxml.jackson.annotation.JsonProperty;

/**
 * @author Víctor Gil
 *
 * since March 2019
 */
public class ConfigValues {
    private static final Logger log = LoggerFactory.getLogger(ConfigValues.class);
    
    @JsonProperty
    private String brokerHost;

    @JsonProperty
    private String queueName;
    
    @JsonProperty
    private String username;
    
    @JsonProperty
    private String password;
    
    public String getBrokerHost() {
        return brokerHost;
    }
    
    public void setBrokerHost(String brokerHost) {
        this.brokerHost = brokerHost;
    }
    
    public String getQueueName() {
        return queueName;
    }
    
    public void setQueueName(String queueName) {
        this.queueName = queueName;
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

