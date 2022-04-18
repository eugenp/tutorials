package com.baeldung.camel.boot.testing;

import org.apache.camel.EndpointInject;
import org.apache.camel.ProducerTemplate;
import org.apache.camel.component.mock.MockEndpoint;
import org.apache.camel.test.spring.junit5.CamelSpringBootTest;
import org.apache.camel.test.spring.junit5.MockEndpoints;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;

@SpringBootTest
@CamelSpringBootTest
@MockEndpoints("file:output")
class GreetingsFileRouterUnitTest {

    @Autowired
    private ProducerTemplate template;

    @EndpointInject("mock:file:output")
    private MockEndpoint mock;

    @Test
    void whenSendBody_thenGreetingReceivedSuccessfully() throws InterruptedException {
        mock.expectedBodiesReceived("Hello Baeldung Readers!");

        template.sendBody("direct:start", null);

        mock.assertIsSatisfied();
    }

}
