package com.baeldung.apache.curator;

import org.apache.curator.RetryPolicy;
import org.apache.curator.framework.CuratorFramework;
import org.apache.curator.framework.CuratorFrameworkFactory;
import org.apache.curator.retry.RetryNTimes;
import org.junit.Before;

public abstract class BaseManualTest {

    @Before
    public void setup() {
        org.apache.log4j.BasicConfigurator.configure();
    }

    protected CuratorFramework newClient() {
        int sleepMsBetweenRetries = 100;
        int maxRetries = 3;
        RetryPolicy retryPolicy = new RetryNTimes(maxRetries, sleepMsBetweenRetries);
        return CuratorFrameworkFactory.newClient("127.0.0.1:2181", retryPolicy);
    }
}
