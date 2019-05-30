package com.baeldung.hexagonal.web;

import static javax.ws.rs.client.Entity.entity;
import static org.junit.jupiter.api.Assertions.assertEquals;

import com.baeldung.hexagonal.domain.CreatePersonRequest;
import javax.ws.rs.core.Application;
import javax.ws.rs.core.MediaType;
import org.glassfish.jersey.client.ClientConfig;
import org.glassfish.jersey.server.ResourceConfig;
import org.glassfish.jersey.test.JerseyTest;
import org.glassfish.jersey.test.TestProperties;
import org.junit.jupiter.api.AfterEach;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;

public class WebIntegrationTest extends JerseyTest {

    /**
     * delegate to JUnit 4 setUp method in parent class
     */
    @BeforeEach
    void before() throws Exception {
        setUp();
    }

    /**
     * delegate to JUnit 4 tearDown method in parent class
     */
    @AfterEach
    void after() throws Exception {
        tearDown();
    }

    /**
     * {@inheritDoc}
     *
     * <p>Configure the JAX-RS client with the smae Jackson configuration used by the server so that
     * it may deserialize types using the same converntions.
     */
    @Override
    protected void configureClient(ClientConfig config) {
        super.configureClient(config);
        config.register(new ObjectMapperProvider(new ObjectMapperFactory().provide()));
    }

    /**
     * {@inheritDoc}
     *
     * @return configuration containing the {@link ResourceConfig}
     */
    @Override
    protected Application configure() {
        // set the port to 0 so that it will pick next available
        // this allows us to run parallel tests
        forceSet(TestProperties.CONTAINER_PORT, "0");

        return ResourceConfig.forApplication(new PersonResourceConfiguration());
    }

    /**
     * Creates a Person, then requests that Person by ID and asserts that each returned person is
     * identical.
     */
    @Test
    void givenCreatedNewPersonResource_whenRequestPersonResource_thenReturnsExpectedResource() {
        final CreatePersonRequest createPersonRequest = new CreatePersonRequest("Joe", "Schmoe");
        final PersonDTO createdPerson = target("person").request(MediaType.APPLICATION_JSON_TYPE)
            .post(entity(createPersonRequest, MediaType.APPLICATION_JSON_TYPE), PersonDTO.class);

        final PersonDTO getPerson = target("person").path(String.valueOf(createdPerson.getId()))
            .request(MediaType.APPLICATION_JSON_TYPE)
            .get(PersonDTO.class);

        assertEquals(createdPerson, getPerson);
    }

}
