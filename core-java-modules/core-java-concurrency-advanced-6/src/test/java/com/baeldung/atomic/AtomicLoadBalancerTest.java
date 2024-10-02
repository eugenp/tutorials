package com.baeldung.atomic;
import org.junit.jupiter.api.Test;

import java.util.HashMap;
import java.util.List;
import java.util.Map;

import static org.junit.jupiter.api.Assertions.assertEquals;

public class AtomicLoadBalancerTest {

    @Test
    public void testServerSelection() {

        List<String> serverList = List.of("Server 1", "Server 2", "Server 3", "Server 4", "Server 5");
        AtomicLoadBalancer balancer = new AtomicLoadBalancer(serverList);
        int numberOfRequests = 10;
        Map<String, Integer> serverCounts = new HashMap<>();
        for (int i = 0; i < numberOfRequests; i++) {
            String assignedServer = balancer.getServer();
            serverCounts.put(assignedServer, serverCounts.getOrDefault(assignedServer, 0) + 1);
        }
        for (String server : serverList) {
            assertEquals(2, serverCounts.get(server), server + " should be selected exactly twice.");
        }
    }
}