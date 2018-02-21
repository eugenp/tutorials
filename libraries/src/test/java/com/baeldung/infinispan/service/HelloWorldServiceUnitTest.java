package com.baeldung.infinispan.service;

import com.baeldung.infinispan.ConfigurationTest;
import org.junit.Test;

import java.util.concurrent.Callable;
import java.util.function.Consumer;

import static org.assertj.core.api.Java6Assertions.assertThat;

public class HelloWorldServiceUnitTest extends ConfigurationTest {

    @Test
    public void whenGetIsCalledTwoTimes_thenTheSecondShouldHitTheCache() {
        assertThat(timeThis(() -> helloWorldService.findSimpleHelloWorld()))
          .isGreaterThanOrEqualTo(1000);

        assertThat(timeThis(() -> helloWorldService.findSimpleHelloWorld()))
          .isLessThan(100);
    }

    @Test
    public void whenGetIsCalledTwoTimesQuickly_thenTheSecondShouldHitTheCache() {
        assertThat(timeThis(() -> helloWorldService.findExpiringHelloWorld()))
          .isGreaterThanOrEqualTo(1000);

        assertThat(timeThis(() -> helloWorldService.findExpiringHelloWorld()))
          .isLessThan(100);
    }

    @Test
    public void whenGetIsCalledTwoTimesSparsely_thenNeitherShouldHitTheCache()
            throws InterruptedException {

        assertThat(timeThis(() -> helloWorldService.findExpiringHelloWorld()))
          .isGreaterThanOrEqualTo(1000);

        Thread.sleep(1100);

        assertThat(timeThis(() -> helloWorldService.findExpiringHelloWorld()))
          .isGreaterThanOrEqualTo(1000);
    }

    @Test
    public void givenOneEntryIsConfigured_whenTwoAreAdded_thenFirstShouldntBeAvailable() {

        assertThat(timeThis(() -> helloWorldService.findEvictingHelloWorld("key 1")))
          .isGreaterThanOrEqualTo(1000);

        assertThat(timeThis(() -> helloWorldService.findEvictingHelloWorld("key 2")))
          .isGreaterThanOrEqualTo(1000);

        assertThat(timeThis(() -> helloWorldService.findEvictingHelloWorld("key 1")))
          .isGreaterThanOrEqualTo(1000);
    }

    @Test
    public void givenOneEntryIsConfigured_whenTwoAreAdded_thenTheFirstShouldBeAvailable() {

        assertThat(timeThis(() -> helloWorldService.findPassivatingHelloWorld("key 1")))
          .isGreaterThanOrEqualTo(1000);

        assertThat(timeThis(() -> helloWorldService.findPassivatingHelloWorld("key 2")))
          .isGreaterThanOrEqualTo(1000);

        assertThat(timeThis(() -> helloWorldService.findPassivatingHelloWorld("key 1")))
          .isLessThan(100);

    }

}
