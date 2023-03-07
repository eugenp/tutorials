package com.boot.camel.exception;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertTrue;
import static org.springframework.test.annotation.DirtiesContext.ClassMode.AFTER_EACH_TEST_METHOD;

import org.apache.camel.CamelContext;
import org.apache.camel.Exchange;
import org.apache.camel.ExchangePattern;
import org.apache.camel.ProducerTemplate;
import org.apache.camel.test.spring.junit5.CamelSpringBootTest;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.test.annotation.DirtiesContext;

import com.baeldung.camel.boot.Application;

@SpringBootTest(classes = Application.class)
@CamelSpringBootTest
@DirtiesContext(classMode = AFTER_EACH_TEST_METHOD)
class ExceptionThrowingRouteUnitTest {

    @Autowired
    private ProducerTemplate template;

    @Test
    @DirtiesContext
    void whenSendBody_thenExceptionRaisedSuccessfully() {
        CamelContext context = template.getCamelContext();
        Exchange exchange = context.getEndpoint("direct:start-exception")
            .createExchange(ExchangePattern.InOut);

        exchange.getIn().setBody("Hello Baeldung");
        Exchange out = template.send("direct:start-exception", exchange);

        assertTrue(out.isFailed(), "Should be failed");
        assertTrue(out.getException() instanceof IllegalArgumentException, "Should be IllegalArgumentException");
        assertEquals("An exception happened on purpose", out.getException().getMessage());
    }

}
