package com.baeldung.setparam;

import static org.junit.Assert.assertTrue;

import java.io.UnsupportedEncodingException;
import java.net.URLEncoder;

import org.apache.http.HttpEntity;
import org.apache.http.HttpResponse;
import org.apache.http.client.HttpClient;
import org.apache.http.client.methods.HttpGet;
import org.apache.http.impl.client.HttpClientBuilder;
import org.apache.http.util.EntityUtils;
import org.junit.BeforeClass;
import org.junit.Test;

public class UnsanitizedParametersRequestLiveTest {

    private static final String TAG_SCRIPT = "<script>alert('Hello');</script>";

    private static String PARAM_INPUT;

    @BeforeClass
    public static void init() throws UnsupportedEncodingException {
        PARAM_INPUT = URLEncoder.encode(TAG_SCRIPT, "UTF-8");
    }

    @Test
    public void whenInputParameterContainsXss_thenResponseBodyContainsUnsanitizedValue() throws Exception {

        // When
        HttpClient client = HttpClientBuilder.create().build();
        HttpGet method = new HttpGet(String.format("http://localhost:8080/setparam/without-sanitize.jsp?input=%s", PARAM_INPUT));
        HttpResponse httpResponse = client.execute(method);

        // Then
        HttpEntity entity = httpResponse.getEntity();
        String responseBody = EntityUtils.toString(entity, "UTF-8");
        assertTrue(responseBody.contains(TAG_SCRIPT));
    }

}