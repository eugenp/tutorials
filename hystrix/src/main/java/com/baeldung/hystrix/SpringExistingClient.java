package com.baeldung.hystrix;

import org.springframework.beans.factory.annotation.Value;
import org.springframework.stereotype.Component;

@Component("springClient")
public class SpringExistingClient {

    @Value("${remoteservice.timeout}")
    private int remoteServiceDelay;

    @HystrixCircuitBreaker
    public String invokeRemoteService() throws InterruptedException{
        return new RemoteServiceTestSimulator(remoteServiceDelay).execute();
    }

}
