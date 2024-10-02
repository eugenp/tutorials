package com.baeldung.atomic;

class IncomingRequest extends Thread {
    private final AtomicLoadBalancer balancer;
    private final int requestId;

    public IncomingRequest(AtomicLoadBalancer balancer, int requestId) {
        this.balancer = balancer;
        this.requestId = requestId;
    }

    @Override
    public void run() {
        String assignedServer = balancer.getServer();
        System.out.println(String.format("Dispatched request %d to %s", requestId, assignedServer));
    }
}