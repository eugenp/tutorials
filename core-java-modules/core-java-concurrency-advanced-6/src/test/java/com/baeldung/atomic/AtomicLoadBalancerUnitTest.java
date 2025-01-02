package com.baeldung.atomic;
import org.junit.jupiter.api.Test;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import static org.junit.jupiter.api.Assertions.assertEquals;

public class AtomicLoadBalancerUnitTest {
    @Test
    public void givenBalancer_whenDispatchingRequests_thenServersAreSelectedExactlyTwice() throws InterruptedException {
        List<String> serverList = List.of("Server 1", "Server 2", "Server 3", "Server 4", "Server 5");
        AtomicLoadBalancer balancer = new AtomicLoadBalancer(serverList);
        int numberOfRequests = 10;
        Map<String, Integer> serverCounts = new HashMap<>();
        List<IncomingRequest> requestThreads = new ArrayList<>();

        for (int i = 1; i <= numberOfRequests; i++) {
            IncomingRequest request = new IncomingRequest(balancer, i);
            requestThreads.add(request);
            request.start();
        }
        for (IncomingRequest request : requestThreads) {
            request.join();
            String assignedServer = balancer.getServer();
            serverCounts.put(assignedServer, serverCounts.getOrDefault(assignedServer, 0) + 1);
        }
        for (String server : serverList) {
            assertEquals(2, serverCounts.get(server), server + " should be selected exactly twice.");
        }
    }
}