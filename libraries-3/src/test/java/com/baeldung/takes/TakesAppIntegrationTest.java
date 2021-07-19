package com.baeldung.takes;

import static org.junit.Assert.assertEquals;

import java.io.IOException;
import java.net.URI;

import org.apache.http.HttpEntity;
import org.apache.http.HttpResponse;
import org.apache.http.client.HttpClient;
import org.apache.http.client.methods.HttpGet;
import org.apache.http.impl.client.HttpClientBuilder;
import org.apache.http.util.EntityUtils;
import org.junit.Test;
import org.takes.http.FtRemote;

public class TakesAppIntegrationTest {

    @Test
    public void givenTake_whenRunRemoteServer_thenRespond() throws Exception {
        new FtRemote(new TakesContact()).exec(
            new FtRemote.Script() {
                @Override
                public void exec(final URI home) throws IOException {
                    HttpClient client = HttpClientBuilder.create().build();    
                    HttpResponse response = client.execute(new HttpGet(home));
                    int statusCode = response.getStatusLine().getStatusCode();
                    HttpEntity entity = response.getEntity();
                    String result = EntityUtils.toString(entity);
                    
                    assertEquals(200, statusCode);
                    assertEquals("Contact us at https://www.baeldung.com", result);
                }
            });
    }
}
