package com.baeldung.rpc.finagle;

import com.twitter.finagle.Service;
import com.twitter.finagle.SimpleFilter;
import com.twitter.finagle.http.Request;
import com.twitter.finagle.http.Response;
import com.twitter.util.Future;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

public class LogFilter extends SimpleFilter<Request, Response> {

    private static final Logger logger = LoggerFactory.getLogger(LogFilter.class);

    @Override
    public Future<Response> apply(Request request, Service<Request, Response> service) {
        logger.info("Request host:" + request.host().getOrElse(() -> ""));
        logger.info("Request params:");
        request.getParams().forEach(entry -> logger.info("\t" + entry.getKey() + " : " + entry.getValue()));
        return service.apply(request);
    }
}
