package com.baeldung.spring.cloud.consul.leadership;

import java.util.concurrent.ExecutionException;

import net.kinguin.leadership.consul.factory.SimpleConsulClusterFactory;

public class LeadershipElection {
    public static void main(String[] args) throws ExecutionException, InterruptedException {
        new SimpleConsulClusterFactory()
            .mode(SimpleConsulClusterFactory.MODE_MULTI)
            .debug(true)
            .build()
            .asObservable()
            .subscribe(i -> System.out.println(i));
    }
}
