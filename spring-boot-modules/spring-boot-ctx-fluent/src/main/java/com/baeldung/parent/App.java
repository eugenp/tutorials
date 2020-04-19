package com.baeldung.parent;

import org.springframework.boot.WebApplicationType;
import org.springframework.boot.builder.SpringApplicationBuilder;

import com.baeldung.ctx1.Ctx1Config;
import com.baeldung.ctx2.Ctx2Config;

public class App {
    public static void main(String[] args) {
        new SpringApplicationBuilder().parent(ParentConfig.class)
            .web(WebApplicationType.NONE)
            .child(Ctx1Config.class)
            .web(WebApplicationType.SERVLET)
            .sibling(Ctx2Config.class)
            .web(WebApplicationType.SERVLET)
            .run(args);
    }
}
