package com.baeldung.concurrent.atomic;

class IncomingRequest extends Thread {

    private final AtomicLoadBalancer loadBalancerInstance;

    public IncomingRequest(AtomicLoadBalancer balancer) {
        this.loadBalancerInstance = balancer;
    }

    @Override
    public void run() {
        String server = this.loadBalancerInstance.getServer();
        System.out.println("Dispatch Request To: "+server);
    }
}