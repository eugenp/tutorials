package com.baeldung.hazelcast.cluster;

import java.util.Map;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import com.hazelcast.core.Hazelcast;
import com.hazelcast.core.HazelcastInstance;
import com.hazelcast.core.IdGenerator;

public class ServerNode {
    private static final Logger logger = LoggerFactory.getLogger(ServerNode.class);

    public static void main(String[] args) {
        HazelcastInstance hazelcastInstance = Hazelcast.newHazelcastInstance();
        Map<Long, String> countryMap = hazelcastInstance.getMap("country");
        IdGenerator idGenerator = hazelcastInstance.getIdGenerator("newid");
        for (int i = 0; i < 10; i++) {
            countryMap.put(idGenerator.newId(), "message" + 1);
        }
        logger.info("Country map size: " + countryMap.size());
    }
}
