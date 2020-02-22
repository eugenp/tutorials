package com.baeldung.takes;

import java.io.IOException;

import org.takes.Request;
import org.takes.Response;
import org.takes.Take;
import org.takes.rs.RsWithBody;
import org.takes.rs.RsWithStatus;
import org.takes.rs.RsWithType;

public final class TakesContact implements Take {
    
    @Override
    public Response act(Request req) throws IOException {
        return new RsWithStatus(new RsWithType(new RsWithBody("Contact us at https://www.baeldung.com"), "text/html"), 200);
    }

}
