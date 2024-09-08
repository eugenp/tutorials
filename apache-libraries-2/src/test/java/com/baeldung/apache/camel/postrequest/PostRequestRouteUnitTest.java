package com.baeldung.apache.camel.postrequest;

import com.baeldung.apache.camel.postrequest.Post;
import com.baeldung.apache.camel.postrequest.PostRequestRoute;
import org.apache.camel.EndpointInject;
import org.apache.camel.Exchange;
import org.apache.camel.Produce;
import org.apache.camel.ProducerTemplate;
import org.apache.camel.builder.RouteBuilder;
import org.apache.camel.component.mock.MockEndpoint;
import org.apache.camel.test.junit5.CamelTestSupport;
import org.junit.jupiter.api.Test;

class PostRequestRouteUnitTest extends CamelTestSupport {

    @EndpointInject("mock:result")
    protected MockEndpoint resultEndpoint;

    @Produce("direct:start")
    protected ProducerTemplate template;

    @Test
    public void givenCamelPostRequestRoute_whenMakingAPostRequestToDummyServer_thenAscertainTheMockEndpointReceiveOneMessage() throws Exception {
        resultEndpoint.expectedMessageCount(1);
        resultEndpoint.message(0)
            .header(Exchange.HTTP_RESPONSE_CODE)
            .isEqualTo(201);
        resultEndpoint.message(0)
            .body()
            .isNotNull();

        template.sendBody(new Post(1, "Java 21", "Virtual Thread"));

        resultEndpoint.assertIsSatisfied();
    }

    @Override
    protected RouteBuilder createRouteBuilder() {
        return new PostRequestRoute();
    }

}