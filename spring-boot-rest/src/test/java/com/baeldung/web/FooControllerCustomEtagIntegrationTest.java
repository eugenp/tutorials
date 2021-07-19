package com.baeldung.web;

import static org.apache.commons.lang3.RandomStringUtils.randomAlphabetic;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.get;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.post;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.put;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.header;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.status;

import org.junit.Test;
import org.junit.runner.RunWith;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.web.servlet.AutoConfigureMockMvc;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.http.MediaType;
import org.springframework.test.context.junit4.SpringRunner;
import org.springframework.test.web.servlet.MockMvc;
import org.springframework.test.web.servlet.ResultActions;

import com.baeldung.persistence.model.Foo;
import com.fasterxml.jackson.databind.ObjectMapper;
import com.google.common.net.HttpHeaders;

@RunWith(SpringRunner.class)
@SpringBootTest
@AutoConfigureMockMvc(addFilters = false)
public class FooControllerCustomEtagIntegrationTest {

    @Autowired
    private MockMvc mvc;

    private String FOOS_ENDPOINT = "/foos/";
    private String CUSTOM_ETAG_ENDPOINT_SUFFIX = "/custom-etag";

    private static String serializeFoo(Foo foo) throws Exception {
        ObjectMapper mapper = new ObjectMapper();
        return mapper.writeValueAsString(foo);
    }

    private static String createFooJson() throws Exception {
        return serializeFoo(new Foo(randomAlphabetic(6)));
    }

    private static Foo deserializeFoo(String fooJson) throws Exception {
        ObjectMapper mapper = new ObjectMapper();
        return mapper.readValue(fooJson, Foo.class);
    }

    @Test
    public void givenResourceExists_whenRetrievingResourceUsingCustomEtagEndpoint_thenEtagIsAlsoReturned()
        throws Exception {
        // Given
        String createdResourceUri = this.mvc.perform(post(FOOS_ENDPOINT).contentType(MediaType.APPLICATION_JSON)
            .content(createFooJson()))
            .andExpect(status().isCreated())
            .andReturn()
            .getResponse()
            .getHeader(HttpHeaders.LOCATION);

        // When
        ResultActions result = this.mvc
            .perform(get(createdResourceUri + CUSTOM_ETAG_ENDPOINT_SUFFIX).contentType(MediaType.APPLICATION_JSON));

        // Then
        result.andExpect(status().isOk())
            .andExpect(header().string(HttpHeaders.ETAG, "\"0\""));
    }

    @Test
    public void givenResourceWasRetrieved_whenRetrievingAgainWithEtagUsingCustomEtagEndpoint_thenNotModifiedReturned() throws Exception {
     // Given
        String createdResourceUri = this.mvc.perform(post(FOOS_ENDPOINT).contentType(MediaType.APPLICATION_JSON)
            .content(createFooJson()))
            .andExpect(status().isCreated())
            .andReturn()
            .getResponse()
            .getHeader(HttpHeaders.LOCATION);
        ResultActions findOneResponse = this.mvc
            .perform(get(createdResourceUri + CUSTOM_ETAG_ENDPOINT_SUFFIX).contentType(MediaType.APPLICATION_JSON));
        String etag = findOneResponse.andReturn().getResponse().getHeader(HttpHeaders.ETAG);

        // When
        ResultActions result = this.mvc
            .perform(get(createdResourceUri + CUSTOM_ETAG_ENDPOINT_SUFFIX).contentType(MediaType.APPLICATION_JSON).header(HttpHeaders.IF_NONE_MATCH, etag));

        // Then
        result.andExpect(status().isNotModified());
    }

    @Test
    public void givenResourceWasRetrievedThenModified_whenRetrievingAgainWithEtagUsingCustomEtagEndpoint_thenResourceIsReturned() throws Exception {
     // Given
        String createdResourceUri = this.mvc.perform(post(FOOS_ENDPOINT).contentType(MediaType.APPLICATION_JSON)
            .content(createFooJson()))
            .andExpect(status().isCreated())
            .andReturn()
            .getResponse()
            .getHeader(HttpHeaders.LOCATION);
        ResultActions findOneResponse = this.mvc
            .perform(get(createdResourceUri + CUSTOM_ETAG_ENDPOINT_SUFFIX)
              .contentType(MediaType.APPLICATION_JSON).accept(MediaType.APPLICATION_JSON));
        String etag = findOneResponse.andReturn().getResponse().getHeader(HttpHeaders.ETAG);
        Foo createdFoo = deserializeFoo(findOneResponse.andReturn().getResponse().getContentAsString());
        createdFoo.setName("updated name");
        this.mvc
        .perform(put(createdResourceUri).contentType(MediaType.APPLICATION_JSON).content(serializeFoo(createdFoo)));

        // When
        ResultActions result = this.mvc
            .perform(get(createdResourceUri + CUSTOM_ETAG_ENDPOINT_SUFFIX).contentType(MediaType.APPLICATION_JSON).header(HttpHeaders.IF_NONE_MATCH, etag));

        // Then
        result.andExpect(status().isOk())
        .andExpect(header().string(HttpHeaders.ETAG, "\"1\""));
    }

}
