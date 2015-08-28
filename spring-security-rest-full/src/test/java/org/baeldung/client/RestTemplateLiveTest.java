package org.baeldung.client;

import com.fasterxml.jackson.databind.JsonNode;
import com.fasterxml.jackson.databind.ObjectMapper;
import org.apache.http.auth.AuthScope;
import org.apache.http.auth.UsernamePasswordCredentials;
import org.apache.http.client.config.RequestConfig;
import org.apache.http.impl.client.BasicCredentialsProvider;
import org.apache.http.impl.client.CloseableHttpClient;
import org.apache.http.impl.client.HttpClientBuilder;
import org.baeldung.persistence.model.Foo;
import org.baeldung.persistence.model.Repository;
import org.baeldung.spring.PersistenceConfig;
import org.junit.Before;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.springframework.http.HttpEntity;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.http.client.ClientHttpRequestFactory;
import org.springframework.http.client.HttpComponentsClientHttpRequestFactory;
import org.springframework.http.converter.HttpMessageConverter;
import org.springframework.http.converter.json.MappingJackson2HttpMessageConverter;
import org.springframework.test.context.ContextConfiguration;
import org.springframework.test.context.junit4.SpringJUnit4ClassRunner;
import org.springframework.web.client.RestTemplate;

import java.io.IOException;
import java.util.ArrayList;
import java.util.List;

import static org.hamcrest.CoreMatchers.is;
import static org.hamcrest.CoreMatchers.not;
import static org.hamcrest.CoreMatchers.notNullValue;
import static org.hamcrest.MatcherAssert.assertThat;

@RunWith(SpringJUnit4ClassRunner.class)
@ContextConfiguration(classes = { PersistenceConfig.class })
public class RestTemplateLiveTest {

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

    @Test
    public void givenFooService_whenPostForObject_thenCreatedObjectIsReturned() {
        String fooService = "http://localhost:8080/spring-security-rest-full/foos";

        ClientHttpRequestFactory requestFactory = getClientHttpRequestFactory();
        RestTemplate restTemplate = new RestTemplate(requestFactory);

        HttpEntity<Foo> request = new HttpEntity<>(new Foo("bar"));
        Foo foo = restTemplate.postForObject(fooService, request, Foo.class);
        assertThat(foo, notNullValue());
        assertThat(foo.getName(), is("bar"));
    }

    @Test
    public void givenFooService_whenPostFor2Objects_thenNewObjectIsCreatedEachTime() {
        String fooService = "http://localhost:8080/spring-security-rest-full/foos";

        ClientHttpRequestFactory requestFactory = getClientHttpRequestFactory();
        RestTemplate restTemplate = new RestTemplate(requestFactory);

        HttpEntity<Foo> request = new HttpEntity<>(new Foo("bar"));
        Foo firstInstance = restTemplate.postForObject(fooService, request, Foo.class);
        Foo secondInstance = restTemplate.postForObject(fooService, request, Foo.class);
        assertThat(firstInstance, notNullValue());
        assertThat(secondInstance, notNullValue());
        assertThat(firstInstance, not(secondInstance));
    }

    private ClientHttpRequestFactory getClientHttpRequestFactory() {
        int timeout = 5;
        RequestConfig config = RequestConfig.custom()
                .setConnectTimeout(timeout * 1000)
                .setConnectionRequestTimeout(timeout * 1000)
                .setSocketTimeout(timeout * 1000).build();

        BasicCredentialsProvider credentialsProvider = new BasicCredentialsProvider();
        credentialsProvider.setCredentials(new AuthScope("localhost", 8080, AuthScope.ANY_REALM),
                new UsernamePasswordCredentials("user1", "user1Pass"));

        CloseableHttpClient client = HttpClientBuilder.create()
                .setDefaultRequestConfig(config)
                .setDefaultCredentialsProvider(credentialsProvider).build();

        return new HttpComponentsClientHttpRequestFactory(client);
    }
}
