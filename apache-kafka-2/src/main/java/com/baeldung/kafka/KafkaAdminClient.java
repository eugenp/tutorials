package com.baeldung.kafka;

import java.util.Collection;
import java.util.Properties;
import java.util.concurrent.ExecutionException;

import org.apache.kafka.clients.admin.AdminClient;
import org.apache.kafka.common.Node;

public class KafkaAdminClient {
    private final AdminClient client;

    public KafkaAdminClient(String bootstrap) {
        Properties props = new Properties();
        props.put("bootstrap.servers", bootstrap);
        props.put("request.timeout.ms", 3000);
        props.put("connections.max.idle.ms", 5000);

        this.client = AdminClient.create(props);
    }

    public boolean verifyConnection() throws ExecutionException, InterruptedException {
        Collection<Node> nodes = this.client.describeCluster()
            .nodes()
            .get();
        return nodes != null && nodes.size() > 0;
    }

    public static void main(String[] args) throws ExecutionException, InterruptedException {
        String defaultBootStrapServer = "localhost:9092";
        KafkaAdminClient kafkaAdminClient = new KafkaAdminClient(defaultBootStrapServer);
        System.out.println(kafkaAdminClient.verifyConnection());
    }

}
