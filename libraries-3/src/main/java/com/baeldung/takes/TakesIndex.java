package com.baeldung.takes;

import java.io.IOException;

import org.takes.Request;
import org.takes.Response;
import org.takes.Take;
import org.takes.rs.RsHtml;
import org.takes.rs.RsVelocity;

public final class TakesIndex implements Take {
    
    @Override
    public Response act(final Request req) throws IOException {

        return new RsHtml(new RsVelocity(this.getClass().getResource("/templates/index.vm") ,new RsVelocity.Pair("userName", "Anshul")));
    }

}
