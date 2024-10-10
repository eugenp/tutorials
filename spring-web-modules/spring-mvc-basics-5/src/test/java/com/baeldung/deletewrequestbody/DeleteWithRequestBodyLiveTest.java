package com.baeldung.deletewrequestbody;

import static org.junit.jupiter.api.Assertions.assertEquals;

import java.io.IOException;

import org.junit.jupiter.api.BeforeAll;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.boot.test.context.SpringBootTest;

import com.baeldung.deletewrequestbody.client.Apache5DeleteClient;
import com.baeldung.deletewrequestbody.client.ApacheDeleteClient;
import com.baeldung.deletewrequestbody.client.PlainDeleteClient;
import com.baeldung.deletewrequestbody.client.SpringTemplateDeleteClient;
import com.baeldung.deletewrequestbody.model.Body;
import com.fasterxml.jackson.core.JsonProcessingException;
import com.fasterxml.jackson.databind.ObjectMapper;

@SpringBootTest
class DeleteWithRequestBodyLiveTest {

    static String json;
    static String url;

    @BeforeAll
    static void setup(@Value("${server.port:8080}") int port, @Value("${server.servlet.context-path:/}") String context) throws JsonProcessingException {
        url = String.format("http://localhost:%d%s/delete", port, context);

        Body body = new Body();
        body.setName("foo");
        body.setValue(1);

        json = new ObjectMapper().writeValueAsString(body);
    }

    @Test
    void whenRestTemplate_thenDeleteWithBody() {
        SpringTemplateDeleteClient client = new SpringTemplateDeleteClient(url);

        assertEquals(json, client.delete(json));
    }

    @Test
    void whenPlainHttpClient_thenDeleteWithBody() throws IOException, InterruptedException {
        PlainDeleteClient client = new PlainDeleteClient(url);

        assertEquals(json, client.delete(json));
    }

    @Test
    void whenApacheHttpClient_thenDeleteWithBody() throws IOException {
        ApacheDeleteClient client = new ApacheDeleteClient(url);

        assertEquals(json, client.delete(json));
    }

    @Test
    void whenApacheHttpClient5_thenDeleteWithBody() throws IOException {
        Apache5DeleteClient client = new Apache5DeleteClient(url);

        assertEquals(json, client.delete(json));
    }
}
