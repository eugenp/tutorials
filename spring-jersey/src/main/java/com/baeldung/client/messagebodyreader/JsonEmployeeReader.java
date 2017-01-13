package com.baeldung.client.messagebodyreader;

import java.io.IOException;
import java.io.InputStream;
import java.lang.annotation.Annotation;
import java.lang.reflect.Type;

import javax.ws.rs.Consumes;
import javax.ws.rs.WebApplicationException;
import javax.ws.rs.core.MediaType;
import javax.ws.rs.core.MultivaluedMap;
import javax.ws.rs.ext.MessageBodyReader;
import javax.ws.rs.ext.Provider;

import com.baeldung.server.model.Employee;
import com.fasterxml.jackson.databind.ObjectMapper;

@Provider
@Consumes(MediaType.APPLICATION_JSON)
public class JsonEmployeeReader implements MessageBodyReader<Employee> {

    @Override
    public boolean isReadable(Class<?> type, Type genericType, Annotation[] annotations, MediaType mediaType) {
        return (type.equals(Employee.class) && mediaType.toString().toString().startsWith(MediaType.APPLICATION_JSON));
    }

    @Override
    public Employee readFrom(Class<Employee> type, Type genericType, Annotation[] annotations, MediaType mediaType, MultivaluedMap<String, String> httpHeaders, InputStream entityStream) throws IOException, WebApplicationException {
        ObjectMapper mapper = new ObjectMapper();
        return mapper.readValue(entityStream, Employee.class);
    }
}
