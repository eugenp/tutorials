package com.baeldung.hazelcast.cluster;

import java.util.Map.Entry;

import com.hazelcast.client.HazelcastClient;
import com.hazelcast.client.config.ClientConfig;
import com.hazelcast.config.GroupConfig;
import com.hazelcast.core.HazelcastInstance;
import com.hazelcast.core.IMap;

public class NativeClient {

    public static void main(String[] args) throws InterruptedException {
        ClientConfig config = new ClientConfig();
        GroupConfig groupConfig = config.getGroupConfig();
        groupConfig.setName("dev");
        groupConfig.setPassword("dev-pass");
        HazelcastInstance hazelcastInstanceClient = HazelcastClient.newHazelcastClient(config);
        IMap<Long, String> map = hazelcastInstanceClient.getMap("data");
        for (Entry<Long, String> entry : map.entrySet()) {
            System.out.println(String.format("Key: %d, Value: %s", entry.getKey(), entry.getValue()));
        }
    }
}
