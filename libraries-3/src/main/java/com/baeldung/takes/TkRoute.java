package com.baeldung.takes;

import org.takes.Request;
import org.takes.Response;
import org.takes.Take;
import org.takes.rs.RsText;

public final class TkRoute implements Take {
    
    @Override
    public Response act(final Request request) {
        return new RsText("Hello, world!");
    }

}
