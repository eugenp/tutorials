package com.baeldung.takes;

import javax.servlet.ServletContext;

import org.takes.Request;
import org.takes.Response;
import org.takes.Take;
import org.takes.rs.RsText;

public final class TakesServletApp implements Take {
    
    private final ServletContext ctx;

    public TakesServletApp(final ServletContext context) {
        this.ctx = context;
    }

    @Override
    public Response act(final Request req) {
        return new RsText("Hello servlet!");
    }
}

