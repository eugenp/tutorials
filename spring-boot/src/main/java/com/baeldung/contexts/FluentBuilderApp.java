package com.baeldung.contexts;

import org.springframework.boot.WebApplicationType;
import org.springframework.boot.builder.SpringApplicationBuilder;

import com.baeldung.context1.Context1Config;
import com.baeldung.context2.Context2Config;


public class FluentBuilderApp {
    public static void main(String[] args) {
        new SpringApplicationBuilder().parent(ParentConfig.class)
            .web(WebApplicationType.NONE)
            .child(Context1Config.class)
            .web(WebApplicationType.SERVLET)
            .sibling(Context2Config.class)
            .web(WebApplicationType.SERVLET)
            .run(args);
    }
}