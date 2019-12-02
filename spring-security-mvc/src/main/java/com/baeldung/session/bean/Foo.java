package com.baeldung.session.bean;

import static org.springframework.context.annotation.ScopedProxyMode.TARGET_CLASS;
import static org.springframework.web.context.WebApplicationContext.SCOPE_SESSION;

import java.time.LocalDateTime;
import java.time.format.DateTimeFormatter;

import org.springframework.context.annotation.Scope;
import org.springframework.stereotype.Component;

@Component
@Scope(value = SCOPE_SESSION, proxyMode = TARGET_CLASS)
public class Foo {
    private final String created;

    public Foo() {
        this.created = LocalDateTime.now()
            .format(DateTimeFormatter.ISO_DATE_TIME);
    }

    public Foo(Foo theFoo) {
        this.created = theFoo.created;
    }

    public String getCreated() {
        return created;
    }
}
