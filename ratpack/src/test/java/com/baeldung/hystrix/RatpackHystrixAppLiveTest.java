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
public class RatpackHystrixAppLiveTest {

    static MainClassApplicationUnderTest appUnderTest;

    @BeforeClass
    public static void setup() {
        System.setProperty("ratpack.hystrix.timeout", "5000");
        appUnderTest = new MainClassApplicationUnderTest(RatpackHystrixApp.class);
    }

    @Test
    public void whenFetchReactive_thenGotEugenProfile() {
        assertThat(appUnderTest
          .getHttpClient()
          .getText("rx"), containsString("www.baeldung.com"));
    }

    @Test
    public void whenFetchAsync_thenGotEugenProfile() {
        assertThat(appUnderTest
          .getHttpClient()
          .getText("async"), containsString("www.baeldung.com"));
    }

    @Test
    public void whenFetchSync_thenGotEugenProfile() {
        assertThat(appUnderTest
          .getHttpClient()
          .getText("sync"), containsString("www.baeldung.com"));
    }

    @AfterClass
    public static void clean() {
        appUnderTest.close();
    }

}
