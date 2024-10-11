package com.baeldung.atomic;

import java.util.logging.Level;
import java.util.logging.Logger;
class IncomingRequest extends Thread {
    private final AtomicLoadBalancer balancer;
    private final int requestId;

    private Logger logger = Logger.getLogger(IncomingRequest.class.getName());
    public IncomingRequest(AtomicLoadBalancer balancer, int requestId) {
        this.balancer = balancer;
        this.requestId = requestId;
    }

    @Override
    public void run() {
        String assignedServer = balancer.getServer();
        logger.log(Level.INFO, String.format("Dispatched request %d to %s", requestId, assignedServer));
    }
}