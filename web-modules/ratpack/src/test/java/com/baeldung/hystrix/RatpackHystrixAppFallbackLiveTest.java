package com.baeldung.hystrix;

import org.junit.AfterClass;
import org.junit.BeforeClass;
import org.junit.Test;
import ratpack.test.MainClassApplicationUnderTest;

import static org.hamcrest.CoreMatchers.containsString;
import static org.junit.Assert.assertThat;

/**
 * @author aiet
 */
public class RatpackHystrixAppFallbackLiveTest {

    static MainClassApplicationUnderTest appUnderTest;

    @BeforeClass
    public static void setup() {
        System.setProperty("ratpack.hystrix.timeout", "10");
        appUnderTest = new MainClassApplicationUnderTest(RatpackHystrixApp.class);
    }

    @Test
    public void whenFetchReactive_thenGotFallbackProfile() {
        assertThat(appUnderTest
          .getHttpClient()
          .getText("rx"), containsString("reactive fallback profile"));
    }

    @Test
    public void whenFetchAsync_thenGotFallbackProfile() {
        assertThat(appUnderTest
          .getHttpClient()
          .getText("async"), containsString("async fallback profile"));
    }

    @Test
    public void whenFetchSync_thenGotFallbackProfile() {
        assertThat(appUnderTest
          .getHttpClient()
          .getText("sync"), containsString("sync fallback profile"));
    }

    @AfterClass
    public static void clean() {
        appUnderTest.close();
    }

}
