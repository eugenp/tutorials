package com.baeldung.spring;

import org.junit.Test;
import ratpack.test.MainClassApplicationUnderTest;

import java.io.IOException;

import static org.hamcrest.CoreMatchers.containsString;
import static org.junit.Assert.assertEquals;
import static org.junit.Assert.assertThat;

/**
 * @author aiet
 */
public class EmbedRatpackAppIntegrationTest {

    MainClassApplicationUnderTest appUnderTest = new MainClassApplicationUnderTest(EmbedRatpackApp.class);

    @Test
    public void whenSayHello_thenGotWelcomeMessage() {
        assertEquals("hello baeldung!", appUnderTest
          .getHttpClient()
          .getText("/hello"));
    }

    @Test
    public void whenRequestList_thenGotArticles() throws IOException {
        assertEquals(3, appUnderTest
          .getHttpClient()
          .getText("/list")
          .split(",").length);
    }

    @Test
    public void whenRequestStaticResource_thenGotStaticContent() {
        assertThat(appUnderTest
          .getHttpClient()
          .getText("/"), containsString("page is static"));
    }

}
