package org.baeldung;

import com.fasterxml.jackson.databind.JsonNode;
import com.fasterxml.jackson.databind.ObjectMapper;
import org.junit.Before;
import org.junit.Test;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.http.converter.HttpMessageConverter;
import org.springframework.http.converter.json.MappingJackson2HttpMessageConverter;
import org.springframework.web.client.RestTemplate;

import java.io.IOException;
import java.util.ArrayList;
import java.util.List;

import static org.hamcrest.CoreMatchers.is;
import static org.hamcrest.MatcherAssert.assertThat;

public class RestTemplateTest {

    RestTemplate restTemplate;
    private List<HttpMessageConverter<?>> messageConverters;
    private final String userReposUrl = "https://api.github.com/users/eugenp/repos";
    private final String repoUrl = "https://api.github.com/repos/eugenp/tutorials";

    @Before
    public void beforeTest() {
        restTemplate = new RestTemplate();

        messageConverters = new ArrayList<>();
        MappingJackson2HttpMessageConverter jsonMessageConverter = new MappingJackson2HttpMessageConverter();
        jsonMessageConverter.setObjectMapper(new ObjectMapper());
        messageConverters.add(jsonMessageConverter);
    }

    @Test
    public void givenValidEndpoint_whenSendGetForRequestEntity_thenStatusOk() throws IOException {
        ResponseEntity<String> response = restTemplate.getForEntity(userReposUrl, String.class);
        assertThat(response.getStatusCode(), is(HttpStatus.OK));
    }

    @Test
    public void givenRepoEndpoint_whenSendGetForRestEntity_thenReceiveCorrectRepoJson() throws IOException {
        ResponseEntity<String> response = restTemplate.getForEntity(repoUrl, String.class);

        ObjectMapper mapper = new ObjectMapper();
        JsonNode root = mapper.readTree(response.getBody());

        JsonNode name = root.path("name");
        assertThat(name.asText(), is("tutorials"));

        JsonNode owner = root.path("owner").path("login");
        assertThat(owner.asText(), is("eugenp"));
    }

    @Test
    public void givenRepoEndpoint_whenSendGetForObject_thenReturnsRepoObject() {
        restTemplate.setMessageConverters(messageConverters);
        String repoUrl = "https://api.github.com/repos/eugenp/tutorials";
        Repository repository = restTemplate.getForObject(repoUrl, Repository.class);

        assertThat(repository.getName(), is("tutorials"));
        assertThat(repository.getOwner().getLogin(), is("eugenp"));
    }
}
