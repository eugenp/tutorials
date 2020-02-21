package com.baeldung.takes;

import java.io.IOException;
import java.sql.SQLException;

import org.takes.Request;
import org.takes.Response;
import org.takes.Take;
import org.takes.rs.RsWithBody;
import org.takes.rs.RsWithStatus;
import org.takes.rs.RsWithType;

public final class TakesContact implements Take {
    
    @Override
    public Response act(Request req) throws IOException, SQLException {
        return new RsWithStatus(new RsWithType(new RsWithBody("<html>"
            + "<head>"
            + "<title>Takes Application - Contact</title></head>"
            + "<body style='color:green'>"
            + "Contact us at @baeldung.com"
            + "</body></html>"), "text/html"), 200);
    }


}
