package org.baeldung.embedded;

import java.io.IOException;
import java.util.Map;
import org.apache.http.HttpEntity;
import org.apache.http.HttpResponse;
import org.apache.http.HttpStatus;
import org.apache.http.client.HttpClient;
import org.apache.http.client.methods.HttpGet;
import org.apache.http.impl.client.HttpClientBuilder;
import org.apache.http.util.EntityUtils;
import org.baeldung.embedded.service.UserService;
import org.junit.Assert;
import org.junit.Before;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.springframework.beans.factory.annotation.Autowired;

import com.fasterxml.jackson.databind.ObjectMapper;

@RunWith(EmbeddedTomcatRunner.class)
public class UserIntegrationTest {

    @Autowired
    protected String baseUrl;

    @Autowired
    private UserService userService;

    private String userName = "HarryPotter";

    @Before
    public void setUp() {
        userService.addUser(userName);
    }

    @Test
    public void givenUserName_whenSendGetForHarryPotter_thenHobbyQuidditch() throws IOException {
        String url = baseUrl + "/user/" + userName;

        HttpClient httpClient = HttpClientBuilder.create()
            .build();
        HttpGet getUserRequest = new HttpGet(url);
        getUserRequest.addHeader("Content-type", "application/json");
        HttpResponse response = httpClient.execute(getUserRequest);

        Assert.assertEquals(HttpStatus.SC_OK, response.getStatusLine()
            .getStatusCode());

        HttpEntity responseEntity = response.getEntity();

        Assert.assertNotNull(responseEntity);

        ObjectMapper mapper = new ObjectMapper();
        String retSrc = EntityUtils.toString(responseEntity);
        Map<String, Object> result = mapper.readValue(retSrc, Map.class);

        Assert.assertEquals("Quidditch", result.get("hobby"));
    }
}
