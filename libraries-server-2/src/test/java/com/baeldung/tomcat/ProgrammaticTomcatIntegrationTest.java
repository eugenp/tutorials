package com.baeldung.tomcat;

import org.apache.http.HttpEntity;
import org.apache.http.HttpResponse;
import org.apache.http.HttpStatus;
import org.apache.http.client.methods.HttpGet;
import org.apache.http.impl.client.CloseableHttpClient;
import org.apache.http.impl.client.HttpClientBuilder;
import org.apache.http.util.EntityUtils;
import org.junit.After;
import org.junit.Before;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.junit.runners.BlockJUnit4ClassRunner;

import static junit.framework.Assert.assertEquals;
import static org.junit.Assert.assertNotNull;

/**
 * Created by adi on 1/14/18.
 */
@RunWith(BlockJUnit4ClassRunner.class)
public class ProgrammaticTomcatIntegrationTest {

    private ProgrammaticTomcat tomcat = new ProgrammaticTomcat();

    @Before
    public void setUp() throws Exception {
        tomcat.startTomcat();
    }

    @After
    public void tearDown() throws Exception {
        tomcat.stopTomcat();
    }

    @Test
    public void givenTomcatStarted_whenAccessServlet_responseIsTestAndResponseHeaderIsSet() throws Exception {
        CloseableHttpClient httpClient = HttpClientBuilder.create().build();
        String uri = "http://localhost:" + tomcat.getPort() + "/my-servlet";
        HttpGet getServlet = new HttpGet(uri);

        HttpResponse response = httpClient.execute(getServlet);
        assertEquals(HttpStatus.SC_OK, response.getStatusLine().getStatusCode());

        String myHeaderValue = response.getFirstHeader("myHeader").getValue();
        assertEquals("myHeaderValue", myHeaderValue);

        HttpEntity responseEntity = response.getEntity();
        assertNotNull(responseEntity);

        String responseString = EntityUtils.toString(responseEntity, "UTF-8");
        assertEquals("test", responseString);
    }

}
