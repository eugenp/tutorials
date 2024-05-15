package com.baeldung.jersey.client.interceptor;

import java.io.IOException;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import jakarta.ws.rs.WebApplicationException;
import jakarta.ws.rs.ext.Provider;
import jakarta.ws.rs.ext.WriterInterceptor;
import jakarta.ws.rs.ext.WriterInterceptorContext;

@Provider
public class RequestClientWriterInterceptor implements WriterInterceptor {

    private static final Logger LOG = LoggerFactory.getLogger(RequestClientWriterInterceptor.class);

    @Override
    public void aroundWriteTo(WriterInterceptorContext context) throws IOException, WebApplicationException {
        LOG.info("request writer interceptor in the client side");

        context.getOutputStream()
            .write(("Message added in the writer interceptor in the client side").getBytes());

        context.proceed();
    }

}
