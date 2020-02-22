package com.baeldung.takes;

import java.io.IOException;

import org.takes.Request;
import org.takes.Response;
import org.takes.Take;
import org.takes.rq.form.RqFormSmart;
import org.takes.rs.RsHtml;
import org.takes.rs.RsVelocity;

public final class TakesIndex implements Take {
    
    @Override
    public Response act(final Request req) throws IOException {
        RqFormSmart form = new RqFormSmart(req);
        String username = form.single("username");
        return new RsHtml(new RsVelocity(this.getClass().getResource("/templates/index.vm"), new RsVelocity.Pair("username", username)));
    }

}
