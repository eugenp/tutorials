package com.baeldung.concurrent.atomic;
import java.util.List;
import java.util.concurrent.atomic.AtomicInteger;
import java.util.ArrayList;
public class AtomicLoadBalancer {
    private List<String> serverList;
    private AtomicInteger counter = new AtomicInteger(0);

    public AtomicLoadBalancer(List<String> serverList) {
        this.serverList = serverList;
    }

    public String getServer() {
        int index = counter.getAndUpdate(counter -> (counter + 1) % serverList.size());
        return serverList.get(index);
    }

    public static void main(String[] args) {
        List<String> serverList = List.of("Server 1", "Server 2", "Server 3", "Server 4", "Server 5");
        AtomicLoadBalancer balancer = new AtomicLoadBalancer(serverList);
        for (int i = 0; i < 10; i++) {
            IncomingRequest request = new IncomingRequest(balancer);
            request.start();
        }
    }
}