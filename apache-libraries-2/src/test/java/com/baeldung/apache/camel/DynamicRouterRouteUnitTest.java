package com.baeldung.apache.camel;

import com.baeldung.apache.camel.DynamicRouterRoute;
import org.apache.camel.RoutesBuilder;
import org.apache.camel.component.mock.MockEndpoint;
import org.apache.camel.test.junit5.CamelTestSupport;
import org.junit.jupiter.api.Test;

public class DynamicRouterRouteUnitTest extends CamelTestSupport {

    @Override
    protected RoutesBuilder createRouteBuilder() {
        return new DynamicRouterRoute();
    }

    @Test
    void givenDynamicRouter_whenMockEndpointExpectedMessageCountOneAndMockAsMessageBody_thenMessageSentToDynamicRouter() throws InterruptedException {

        MockEndpoint mockDynamicEndpoint = getMockEndpoint("mock:dynamicRouter");
        mockDynamicEndpoint.expectedMessageCount(1);

        template.send("direct:dynamicRouter", exchange -> exchange.getIn()
            .setBody("mock"));
        MockEndpoint.assertIsSatisfied(context);
    }

    @Test
    void givenDynamicRouter_whenMockEndpointExpectedMessageCountOneAndDirectAsMessageBody_thenMessageSentToDynamicRouter() throws InterruptedException {

        MockEndpoint mockDynamicEndpoint = context.getEndpoint("mock:directDynamicRouter", MockEndpoint.class);
        mockDynamicEndpoint.expectedMessageCount(1);

        template.send("direct:dynamicRouter", exchange -> exchange.getIn()
            .setBody("direct"));

        MockEndpoint.assertIsSatisfied(context);
    }

    @Test
    void givenDynamicRouter_whenMockEndpointExpectedMessageCountOneAndSedaAsMessageBody_thenMessageSentToDynamicRouter() throws InterruptedException {

        MockEndpoint mockDynamicEndpoint = context.getEndpoint("mock:sedaDynamicRouter", MockEndpoint.class);
        mockDynamicEndpoint.expectedMessageCount(1);

        template.send("direct:dynamicRouter", exchange -> exchange.getIn()
            .setBody("seda"));
        MockEndpoint.assertIsSatisfied(context);
    }

    @Test
    void givenDynamicRouter_whenMockEndpointExpectedMessageCountOneAndBookAsMessageBody_thenMessageSentToDynamicRouter() throws InterruptedException {

        MockEndpoint mockDynamicEndpoint = getMockEndpoint("mock:fileDynamicRouter");
        mockDynamicEndpoint.expectedMessageCount(1);

        template.send("direct:dynamicRouter", exchange -> exchange.getIn()
            .setBody("file"));
        MockEndpoint.assertIsSatisfied(context);
    }

}