package com.baeldung.takes;

import java.io.IOException;

import org.takes.Request;
import org.takes.Response;
import org.takes.Take;
import org.takes.rs.RsVelocity;
import org.takes.rs.RsWithBody;
import org.takes.rs.RsWithStatus;
import org.takes.rs.RsWithType;

public final class TakesIndex implements Take {

//    @Override
//    public Response act(Request req) throws IOException {
//        return new RsWithStatus(new RsWithType(new RsWithBody("<html>Hello, world!</html>"), "text/html"), 200);
//    }
    
    @Override
    public Response act(Request req) {
      return new RsVelocity("Hello, ${name}", new RsVelocity.Pair("name", "Jeffrey"));
    }
    
}
