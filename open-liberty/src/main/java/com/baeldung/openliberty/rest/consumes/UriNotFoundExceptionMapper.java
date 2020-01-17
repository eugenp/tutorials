package com.baeldung.openliberty.rest.consumes;

import java.util.logging.Logger;
import javax.ws.rs.core.MultivaluedMap;
import javax.ws.rs.core.Response;
import javax.ws.rs.ext.Provider;
import org.eclipse.microprofile.rest.client.ext.ResponseExceptionMapper;

@Provider
public class UriNotFoundExceptionMapper implements ResponseExceptionMapper<UriNotFoundException> {
    Logger LOG = Logger.getLogger(UriNotFoundException.class.getName());

    @Override
    public boolean handles(int status, MultivaluedMap<String, Object> headers) {
        LOG.info("status = " + status);
        return status == 404;
    }

    @Override
    public UriNotFoundException toThrowable(Response response) {
        return new UriNotFoundException();
    }
}
