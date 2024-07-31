package com.baeldung.setparam;

import static org.junit.Assert.assertTrue;

import java.util.Locale;

import org.apache.http.HttpEntity;
import org.apache.http.HttpResponse;
import org.apache.http.client.HttpClient;
import org.apache.http.client.methods.HttpGet;
import org.apache.http.impl.client.HttpClientBuilder;
import org.apache.http.util.EntityUtils;
import org.junit.Test;

public class LanguageServletLiveTest {

    @Test
    public void whenGetRequestUsingHttpClient_thenResponseBodyContainsDefaultLanguage() throws Exception {

        // When
        HttpClient client = HttpClientBuilder.create().build();
        HttpGet method = new HttpGet("http://localhost:8080/setparam/lang");
        HttpResponse httpResponse = client.execute(method);

        // Then
        Locale defaultLocale = Locale.getDefault();
        String expectedLanguage = defaultLocale.getDisplayLanguage(defaultLocale);

        HttpEntity entity = httpResponse.getEntity();
        String responseBody = EntityUtils.toString(entity, "UTF-8");
        assertTrue(responseBody.contains("The language you have selected: " + expectedLanguage));
    }

}
