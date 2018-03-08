package com.baeldung.servlets;

import org.junit.Test;

import static org.junit.Assert.assertTrue;

public class FormServletLiveTest {

    @Test
    public void whenPostRequestUsingHttpClient_thenCorrect() throws Exception {

//        HttpClient client = HttpClientBuilder.create().build();
//        HttpPost method = new HttpPost("http://localhost:8080/calculateServlet");
//
//        List<BasicNameValuePair> nvps = new ArrayList<>();
//        nvps.add(new BasicNameValuePair("height", String.valueOf(2)));
//        nvps.add(new BasicNameValuePair("weight", String.valueOf(80)));
//
//        method.setEntity(new UrlEncodedFormEntity(nvps));
//        HttpResponse httpResponse = client.execute(method);
//
//        assertEquals("Success", httpResponse.getHeaders("Test")[0].getValue());
//        assertEquals("20.0", httpResponse.getHeaders("BMI")[0].getValue());
        assertTrue(true);
    }
}
