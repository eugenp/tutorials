package com.baeldung.dynamicendpoints.config;

import org.springframework.beans.factory.annotation.Value;
import org.springframework.cloud.context.config.annotation.RefreshScope;
import org.springframework.stereotype.Component;

@Component
@RefreshScope
public class EndpointRefreshConfigBean {

    private boolean foo;

    private String regex;

    public EndpointRefreshConfigBean(@Value("${endpoint.foo}") boolean foo, @Value("${endpoint.regex}") String regex) {
        this.foo = foo;
        this.regex = regex;
    }

    public boolean isFoo() {
        return foo;
    }

    public void setFoo(boolean foo) {
        this.foo = foo;
    }

    public String getRegex() {
        return regex;
    }

    public void setRegex(String regex) {
        this.regex = regex;
    }
}