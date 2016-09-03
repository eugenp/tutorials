package com.baeldung.hazelcast.cluster;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import com.baeldung.hazelcast.listener.CountryEntryListener;
import com.hazelcast.client.HazelcastClient;
import com.hazelcast.client.config.ClientConfig;
import com.hazelcast.config.GroupConfig;
import com.hazelcast.core.HazelcastInstance;
import com.hazelcast.core.IMap;

public class NativeClient {
    private static final Logger logger = LoggerFactory.getLogger(NativeClient.class);

    public static void main(String[] args) throws InterruptedException {
        ClientConfig config = new ClientConfig();
        GroupConfig groupConfig = config.getGroupConfig();
        groupConfig.setName("dev");
        groupConfig.setPassword("dev-pass");
        HazelcastInstance hazelcastInstanceClient = HazelcastClient.newHazelcastClient(config);
        IMap<Long, String> countryMap = hazelcastInstanceClient.getMap("country");
        countryMap.addEntryListener(new CountryEntryListener(), true);
        logger.info("Country map size: " + countryMap.size());
    }
}
