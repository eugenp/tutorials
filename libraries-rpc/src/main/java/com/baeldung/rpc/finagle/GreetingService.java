package com.baeldung.rpc.finagle;

import com.twitter.finagle.Service;
import com.twitter.finagle.http.Request;
import com.twitter.finagle.http.Response;
import com.twitter.finagle.http.Status;
import com.twitter.io.Buf;
import com.twitter.io.Reader;
import com.twitter.util.Future;

public class GreetingService extends Service<Request, Response> {
    @Override
    public Future<Response> apply(Request request) {
        String greeting = "Hello " + request.getParam("name");
        Reader<Buf> reader = Reader.fromBuf(new Buf.ByteArray(greeting.getBytes(), 0, greeting.length()));
        return Future.value(Response.apply(request.version(), Status.Ok(), reader));
    }
}
