package com.baeldung.jersey.server.config;

import com.baeldung.jersey.server.Greetings;
import com.baeldung.jersey.server.filter.ResponseServerFilter;

import jakarta.ws.rs.container.DynamicFeature;
import jakarta.ws.rs.container.ResourceInfo;
import jakarta.ws.rs.core.FeatureContext;
import jakarta.ws.rs.ext.Provider;

@Provider
public class HelloDynamicBinding implements DynamicFeature {

    @Override
    public void configure(ResourceInfo resourceInfo, FeatureContext context) {

        if (Greetings.class.equals(resourceInfo.getResourceClass()) && resourceInfo.getResourceMethod()
            .getName()
            .contains("HiGreeting")) {
            context.register(ResponseServerFilter.class);
        }

    }

}
