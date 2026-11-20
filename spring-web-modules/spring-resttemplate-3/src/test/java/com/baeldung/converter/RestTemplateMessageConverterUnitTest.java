package com.baeldung.converter;

import com.baeldung.converter.config.RestTemplateConverterConfig;
import com.baeldung.converter.model.User;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Qualifier;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.core.ParameterizedTypeReference;
import org.springframework.http.HttpMethod;
import org.springframework.http.MediaType;
import org.springframework.http.ResponseEntity;
import org.springframework.test.web.client.MockRestServiceServer;
import org.springframework.web.client.RestTemplate;

import java.util.List;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertNotNull;
import static org.springframework.test.web.client.match.MockRestRequestMatchers.requestTo;
import static org.springframework.test.web.client.response.MockRestResponseCreators.withSuccess;

@SpringBootTest(classes = RestTemplateConverterConfig.class)
class RestTemplateMessageConverterUnitTest {

    @Autowired
    private RestTemplate restTemplate;

    @Autowired
    @Qualifier("specificMediaTypesRestTemplate")
    private RestTemplate specificMediaTypesRestTemplate;

    // Jackson converter with explicit media types deserializes text/plain response into User
    @Test
    void givenSpecificMediaTypesRestTemplate_whenTextPlainResponse_thenDeserializeCorrectly() {
        MockRestServiceServer mockServer = MockRestServiceServer.createServer(specificMediaTypesRestTemplate);

        mockServer.expect(requestTo("/user"))
            .andRespond(withSuccess("{\"id\":1,\"name\":\"Sudarshan\"}", MediaType.TEXT_PLAIN));

        User user = specificMediaTypesRestTemplate.getForObject("/user", User.class);

        assertNotNull(user);
        assertEquals("Sudarshan", user.getName());
    }

    // Jackson converter with MediaType.ALL deserializes text/plain response into User
    @Test
    void givenMockServer_whenTextPlainResponse_thenDeserializeCorrectly() {
        MockRestServiceServer mockServer = MockRestServiceServer.createServer(restTemplate);

        mockServer.expect(requestTo("/user"))
            .andRespond(withSuccess("{\"id\":1,\"name\":\"Sudarshan\"}", MediaType.TEXT_PLAIN));

        User user = restTemplate.getForObject("/user", User.class);

        assertNotNull(user);
        assertEquals("Sudarshan", user.getName());
    }

    // restTemplate.exchange() with ParameterizedTypeReference preserves generic type to deserialize List<User>
    @Test
    void givenMockServer_whenTextPlainResponseForList_thenDeserializeWithParameterizedTypeReference() {
        MockRestServiceServer mockServer = MockRestServiceServer.createServer(restTemplate);

        mockServer.expect(requestTo("/users"))
            .andRespond(withSuccess("[{\"id\":1,\"name\":\"Sudarshan\"},{\"id\":2,\"name\":\"Baeldung\"}]", MediaType.TEXT_PLAIN));

        ResponseEntity<List<User>> response = restTemplate.exchange(
            "/users",
            HttpMethod.GET,
            null,
            new ParameterizedTypeReference<List<User>>() {}
        );

        assertNotNull(response.getBody());
        assertEquals(2, response.getBody().size());
        assertEquals("Sudarshan", response.getBody().get(0).getName());
    }
}
