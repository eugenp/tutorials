package com.baeldung.spring.cloud.zuul.filter;

import java.net.ConnectException;
import java.time.Instant;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.stereotype.Component;

import com.netflix.zuul.ZuulFilter;
import com.netflix.zuul.context.RequestContext;
import com.netflix.zuul.exception.ZuulException;

@Component
public class CustomZuulErrorFilter extends ZuulFilter {

    private static final Logger LOG = LoggerFactory.getLogger(CustomZuulErrorFilter.class);

    private static String RESPONSE_BODY = "{\n" + "    \"timestamp\": " + "\"" + Instant.now()
        .toString() + "\"" + ",\n" + "    \"status\": 503,\n" + "    \"error\": \"Service Unavailable\"\n" + "}";

    @Override
    public Object run() {
        RequestContext context = RequestContext.getCurrentContext();
        Throwable throwable = context.getThrowable();

        if (throwable instanceof ZuulException) {
            final ZuulException zuulException = (ZuulException) throwable;
            LOG.error("Zuul exception: " + zuulException.getMessage());

            if (throwable.getCause()
                .getCause()
                .getCause() instanceof ConnectException) {

                // reset throwable to prevent further error handling in follow up filters
                context.remove("throwable");

                // set custom response attributes
                context.setResponseBody(RESPONSE_BODY);
                context.getResponse()
                    .setContentType("application/json");
                context.setResponseStatusCode(503);
            }

        }
        return null;
    }

    @Override
    public boolean shouldFilter() {
        // always filter
        return true;
    }

    @Override
    public int filterOrder() {
        return -1;
    }

    @Override
    public String filterType() {
        // error filter type
        return "error";
    }

}
