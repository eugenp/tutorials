package com.baeldung.camel.exception;

import org.apache.camel.EndpointInject;
import org.apache.camel.ProducerTemplate;
import org.apache.camel.component.mock.MockEndpoint;
import org.apache.camel.test.spring.junit5.CamelSpringBootTest;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;

@SpringBootTest
@CamelSpringBootTest
class ExceptionHandlingWithDoTryRouteUnitTest {

    @Autowired
    private ProducerTemplate template;

    @EndpointInject("mock:caught")
    private MockEndpoint mock;

    @Test
    void whenSendHeaders_thenExceptionRaisedAndHandledSuccessfully() throws Exception {
        mock.expectedMessageCount(1);

        template.sendBodyAndHeader("direct:start-handling-exception", null, "fruit", "Banana");

        mock.assertIsSatisfied();
    }

}
