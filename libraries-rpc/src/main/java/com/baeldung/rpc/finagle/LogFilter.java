package com.baeldung.rpc.finagle;

import com.twitter.finagle.Service;
import com.twitter.finagle.SimpleFilter;
import com.twitter.finagle.http.Request;
import com.twitter.finagle.http.Response;
import com.twitter.util.Future;

public class LogFilter extends SimpleFilter<Request, Response> {
    @Override
    public Future<Response> apply(Request request, Service<Request, Response> service) {
        System.out.println("Request host:" + request.host().getOrElse(() -> ""));
        System.out.println("Request params:");
        request.getParams().forEach(entry -> System.out.println("\t" + entry.getKey() + " : " + entry.getValue()));
        return service.apply(request);
    }
}
