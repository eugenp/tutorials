package com.baeldung.infinispan.service;

import com.baeldung.infinispan.ConfigurationTest;
import org.junit.Test;

import static org.assertj.core.api.Java6Assertions.assertThat;

public class HelloWorldServiceUnitTest extends ConfigurationTest {

    @Test
    public void whenSimpleGetHelloWorldIsCalledTwoTimes_thenTheSecondOneShouldHitTheCache() {
        long milis = System.currentTimeMillis();

        helloWorldService.findSimpleHelloWorld();
        long executionTime = System.currentTimeMillis() - milis;
        assertThat(executionTime).isGreaterThanOrEqualTo(5000);

        milis = System.currentTimeMillis();

        helloWorldService.findSimpleHelloWorld();
        executionTime = System.currentTimeMillis() - milis;
        assertThat(executionTime).isLessThan(100);
    }

    @Test
    public void whenExpiritingGetHelloWorldIsCalledTwoTimesQuickly_thenTheSecondOneShouldHitTheCache() {
        long milis = System.currentTimeMillis();

        helloWorldService.findExpiringHelloWorld();
        long executionTime = System.currentTimeMillis() - milis;
        assertThat(executionTime).isGreaterThanOrEqualTo(5000);

        milis = System.currentTimeMillis();

        helloWorldService.findExpiringHelloWorld();
        executionTime = System.currentTimeMillis() - milis;
        assertThat(executionTime).isLessThan(100);
    }

    @Test
    public void whenExpiringGetHelloWorldIsCalledTwoTimesSparsely_thenNeitherShouldHitTheCache() throws InterruptedException {
        long milis = System.currentTimeMillis();

        helloWorldService.findSimpleHelloWorldInExpiringCache();
        long executionTime = System.currentTimeMillis() - milis;
        assertThat(executionTime).isGreaterThanOrEqualTo(5000);

        Thread.sleep(5500);

        milis = System.currentTimeMillis();

        helloWorldService.findSimpleHelloWorldInExpiringCache();
        executionTime = System.currentTimeMillis() - milis;
        assertThat(executionTime).isGreaterThanOrEqualTo(5000);
    }

    @Test
    public void givenThatOnlyOneEntryIsConfiguredInEvictingCache_whenTwoDifferentKeysAreAdded_thenTheFirstKeyShouldNotBeAvailable() throws InterruptedException {
        long milis = System.currentTimeMillis();
        helloWorldService.findEvictingHelloWorld("key 1");
        long executionTime = System.currentTimeMillis() - milis;
        assertThat(executionTime).isGreaterThanOrEqualTo(5000);

        milis = System.currentTimeMillis();
        helloWorldService.findEvictingHelloWorld("key 2");
        executionTime = System.currentTimeMillis() - milis;
        assertThat(executionTime).isGreaterThanOrEqualTo(5000);

        milis = System.currentTimeMillis();
        helloWorldService.findEvictingHelloWorld("key 1");
        executionTime = System.currentTimeMillis() - milis;
        assertThat(executionTime).isGreaterThanOrEqualTo(5000);
    }

    @Test
    public void givenThatOnlyOneEntryIsConfiguredInPassivatingCache_whenTwoDifferentKeysAreAdded_thenTheFirstKeyShouldBeAvailable() throws InterruptedException {
        long milis = System.currentTimeMillis();
        helloWorldService.findPassivatingHelloWorld("key 1");
        long executionTime = System.currentTimeMillis() - milis;
        assertThat(executionTime).isGreaterThanOrEqualTo(5000);

        milis = System.currentTimeMillis();
        helloWorldService.findPassivatingHelloWorld("key 2");
        executionTime = System.currentTimeMillis() - milis;
        assertThat(executionTime).isGreaterThanOrEqualTo(5000);

        milis = System.currentTimeMillis();
        helloWorldService.findPassivatingHelloWorld("key 1");
        executionTime = System.currentTimeMillis() - milis;
        assertThat(executionTime).isLessThan(100);
    }

}
