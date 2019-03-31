package com.baeldung.hexagonalarchitecture.liquiditytracker.config;

/**
 * @author Víctor Gil
 *
 * since March 2019
 */
public class MessagingConfigValues {

    private String brokerHostname;
    private String incomingMessagesQueue;
    private String outgoingMessagesQueue;
    private String username;
    private String password;

    @Override
    public String toString() {
        return "MessagingConfigValues [brokerHostname=" + brokerHostname + ", incomingMessagesQueue="
                + incomingMessagesQueue + ", outgoingMessagesQueue=" + outgoingMessagesQueue + ", username=" + username
                + ", password=" + password + "]";
    }

    // Just getters and setters
    public String getBrokerHostname() {
        return brokerHostname;
    }

    public void setBrokerHostname(String brokerHostname) {
        this.brokerHostname = brokerHostname;
    }

    public String getIncomingMessagesQueue() {
        return incomingMessagesQueue;
    }

    public void setIncomingMessagesQueue(String incomingMessagesQueue) {
        this.incomingMessagesQueue = incomingMessagesQueue;
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

    public String getOutgoingMessagesQueue() {
        return outgoingMessagesQueue;
    }

    public void setOutgoingMessagesQueue(String outgoingMessagesQueue) {
        this.outgoingMessagesQueue = outgoingMessagesQueue;
    }
}
