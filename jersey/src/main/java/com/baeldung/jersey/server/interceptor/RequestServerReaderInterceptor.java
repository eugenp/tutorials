package com.baeldung.jersey.server.interceptor;

import java.io.BufferedReader;
import java.io.ByteArrayInputStream;
import java.io.IOException;
import java.io.InputStream;
import java.io.InputStreamReader;
import java.util.stream.Collectors;

import javax.ws.rs.WebApplicationException;
import javax.ws.rs.ext.Provider;
import javax.ws.rs.ext.ReaderInterceptor;
import javax.ws.rs.ext.ReaderInterceptorContext;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

@Provider
public class RequestServerReaderInterceptor implements ReaderInterceptor {

    private static final Logger LOG = LoggerFactory.getLogger(RequestServerReaderInterceptor.class);

    @Override
    public Object aroundReadFrom(ReaderInterceptorContext context) throws IOException, WebApplicationException {
        LOG.info("Request reader interceptor in the server side");

        InputStream is = context.getInputStream();
        String body = new BufferedReader(new InputStreamReader(is)).lines()
            .collect(Collectors.joining("\n"));

        context.setInputStream(new ByteArrayInputStream((body + " message added in server reader interceptor").getBytes()));

        return context.proceed();
    }

}
