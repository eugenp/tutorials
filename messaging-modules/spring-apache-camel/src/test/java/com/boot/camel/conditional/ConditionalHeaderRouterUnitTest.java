package com.boot.camel.conditional;

import static org.springframework.test.annotation.DirtiesContext.ClassMode.AFTER_EACH_TEST_METHOD;

import org.apache.camel.EndpointInject;
import org.apache.camel.ProducerTemplate;
import org.apache.camel.component.mock.MockEndpoint;
import org.apache.camel.test.spring.junit5.CamelSpringBootTest;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.test.annotation.DirtiesContext;

import com.baeldung.camel.boot.Application;

@SpringBootTest(classes = Application.class)
@CamelSpringBootTest
@DirtiesContext(classMode = AFTER_EACH_TEST_METHOD)
class ConditionalHeaderRouterUnitTest {

    @Autowired
    private ProducerTemplate template;

    @EndpointInject("mock:result")
    private MockEndpoint mock;

    @Test
    @DirtiesContext
    void whenSendBodyWithFruit_thenFavouriteHeaderReceivedSuccessfully() throws InterruptedException {
        mock.expectedHeaderReceived("favourite", "Banana");

        template.sendBodyAndHeader("direct:start-conditional-header", null, "fruit", "Banana");

        mock.assertIsSatisfied();
    }
}
