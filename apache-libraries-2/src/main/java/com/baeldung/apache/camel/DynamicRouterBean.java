package com.baeldung.apache.camel;

import org.apache.camel.ExchangeProperties;

import java.util.Map;

public class DynamicRouterBean {
    public String route(String body, @ExchangeProperties Map<String, Object> properties) {
        int invoked = (int) properties.getOrDefault("invoked", 0) + 1;

        properties.put("invoked", invoked);

        if (invoked == 1) {
            switch (body.toLowerCase()) {
                case "mock":
                    return "mock:dynamicRouter";
                case "direct":
                    return "mock:directDynamicRouter";
                case "seda":
                    return "mock:sedaDynamicRouter";
                case "file":
                    return "mock:fileDynamicRouter";
                default:
                    break;
            }
        }
        return null;
    }
}
