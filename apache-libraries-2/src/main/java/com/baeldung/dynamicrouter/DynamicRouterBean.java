package com.baeldung.dynamicrouter;

import org.apache.camel.ExchangeProperties;

import java.util.Map;

public class DynamicRouterBean {
    public String route(String body, @ExchangeProperties Map<String, Object> properties) {
        int invoked = 0;
        Integer current = (Integer) properties.get("invoked");
        if (current != null) {
            invoked = current;
        }
        invoked++;
        properties.put("invoked", invoked);

        if (body.equalsIgnoreCase("mock") && invoked == 1) {
            return "mock:dynamicRouter";
        } else if (body.equalsIgnoreCase("direct") && invoked == 1) {
            return "mock:directDynamicRouter";
        } else if (body.equalsIgnoreCase("seda") && invoked == 1) {
            return "mock:sedaDynamicRouter";
        } else if (body.equalsIgnoreCase("file") && invoked == 1) {
            return "mock:fileDynamicRouter";
        }
        return null;
    }
}
