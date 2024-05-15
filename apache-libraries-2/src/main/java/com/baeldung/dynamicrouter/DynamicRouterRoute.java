package com.baeldung.dynamicrouter;

import org.apache.camel.builder.RouteBuilder;

public class DynamicRouterRoute extends RouteBuilder {

    @Override
    public void configure() {

        from("direct:dynamicRouter").dynamicRouter(method(DynamicRouterBean.class, "route"));

    }
}
