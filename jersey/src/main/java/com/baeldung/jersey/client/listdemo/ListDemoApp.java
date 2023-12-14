package com.baeldung.jersey.client.listdemo;

import org.glassfish.jersey.server.ResourceConfig;

public class ListDemoApp extends ResourceConfig {
    public ListDemoApp() {
        packages("com.baeldung.jersey.client.listdemo");
    }
}
