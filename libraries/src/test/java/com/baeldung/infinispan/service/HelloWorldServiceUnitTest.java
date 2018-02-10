package com.baeldung.infinispan.service;

import com.baeldung.infinispan.ConfigurationTest;
import org.junit.Test;

import static org.assertj.core.api.Java6Assertions.assertThat;

public class HelloWorldServiceUnitTest extends ConfigurationTest {

    @Test
    public void whenGetIsCalledTwoTimes_thenTheSecondShouldHitTheCache() {
        long milis = System.currentTimeMillis();
        helloWorldService.findSimpleHelloWorld();
        long executionTime = System.currentTimeMillis() - milis;

        assertThat(executionTime).isGreaterThanOrEqualTo(1000);

        milis = System.currentTimeMillis();
        helloWorldService.findSimpleHelloWorld();
        executionTime = System.currentTimeMillis() - milis;

        assertThat(executionTime).isLessThan(100);
    }

    @Test
    public void whenGetIsCalledTwoTimesQuickly_thenTheSecondShouldHitTheCache() {
        long milis = System.currentTimeMillis();
        helloWorldService.findExpiringHelloWorld();
        long executionTime = System.currentTimeMillis() - milis;

        assertThat(executionTime).isGreaterThanOrEqualTo(1000);

        milis = System.currentTimeMillis();
        helloWorldService.findExpiringHelloWorld();
        executionTime = System.currentTimeMillis() - milis;

        assertThat(executionTime).isLessThan(100);
    }

    @Test
    public void whenGetIsCalledTwoTimesSparsely_thenNeitherShouldHitTheCache()
          throws InterruptedException {

        long milis = System.currentTimeMillis();
        helloWorldService.findSimpleHelloWorldInExpiringCache();
        long executionTime = System.currentTimeMillis() - milis;

        assertThat(executionTime).isGreaterThanOrEqualTo(1000);

        Thread.sleep(1100);

        milis = System.currentTimeMillis();
        helloWorldService.findSimpleHelloWorldInExpiringCache();
        executionTime = System.currentTimeMillis() - milis;

        assertThat(executionTime).isGreaterThanOrEqualTo(1000);
    }

    @Test
    public void givenOneEntryIsConfigured_whenTwoAreAdded_thenFirstShouldntBeAvailable()
          throws InterruptedException {

        long milis = System.currentTimeMillis();
        helloWorldService.findEvictingHelloWorld("key 1");
        long executionTime = System.currentTimeMillis() - milis;

        assertThat(executionTime).isGreaterThanOrEqualTo(1000);

        milis = System.currentTimeMillis();
        helloWorldService.findEvictingHelloWorld("key 2");
        executionTime = System.currentTimeMillis() - milis;

        assertThat(executionTime).isGreaterThanOrEqualTo(1000);

        milis = System.currentTimeMillis();
        helloWorldService.findEvictingHelloWorld("key 1");
        executionTime = System.currentTimeMillis() - milis;

        assertThat(executionTime).isGreaterThanOrEqualTo(1000);
    }

    @Test
    public void givenOneEntryIsConfigured_whenTwoAreAdded_thenTheFirstShouldBeAvailable()
          throws InterruptedException {

        long milis = System.currentTimeMillis();
        helloWorldService.findPassivatingHelloWorld("key 1");
        long executionTime = System.currentTimeMillis() - milis;

        assertThat(executionTime).isGreaterThanOrEqualTo(1000);

        milis = System.currentTimeMillis();
        helloWorldService.findPassivatingHelloWorld("key 2");
        executionTime = System.currentTimeMillis() - milis;

        assertThat(executionTime).isGreaterThanOrEqualTo(1000);

        milis = System.currentTimeMillis();
        helloWorldService.findPassivatingHelloWorld("key 1");
        executionTime = System.currentTimeMillis() - milis;

        assertThat(executionTime).isLessThan(100);
    }

}
