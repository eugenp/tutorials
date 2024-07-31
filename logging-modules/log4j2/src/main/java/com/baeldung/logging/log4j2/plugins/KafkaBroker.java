package com.baeldung.logging.log4j2.plugins;

import java.io.Serializable;

public class KafkaBroker implements Serializable {

    private final String ipAddress;
    private final int port;

    public KafkaBroker(String ipAddress, int port, String topic, String partition) {
        this.ipAddress = ipAddress;
        this.port = port;
        this.topic = topic;
        this.partition = partition;
    }

    public String getTopic() {
        return topic;
    }

    public String getPartition() {
        return partition;
    }

    private final String topic;
    private final String partition;


    public String getIpAddress() {
        return ipAddress;
    }

    public int getPort() {
        return port;
    }


}
