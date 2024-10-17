package com.baeldung.atomic;

import java.util.List;
import java.util.concurrent.atomic.AtomicInteger;

public class AtomicLoadBalancer {
    private List<String> serverList;
    private final AtomicInteger counter = new AtomicInteger(0);

    public AtomicLoadBalancer(List<String> serverList) {
        this.serverList = serverList;
    }

    public String getServer() {
        int index = counter.get() % serverList.size();
        counter.incrementAndGet();
        return serverList.get(index);
    }

    public static void main(String[] args) {
        List<String> serverList = List.of("Server 1", "Server 2", "Server 3", "Server 4", "Server 5");
        AtomicLoadBalancer balancer = new AtomicLoadBalancer(serverList);
        for (int i = 1; i <= 10; i++) {
            IncomingRequest request = new IncomingRequest(balancer, i);
            request.start();
        }
    }
}