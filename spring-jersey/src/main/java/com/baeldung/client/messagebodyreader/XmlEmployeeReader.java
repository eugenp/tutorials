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
import javax.xml.bind.JAXB;

import com.baeldung.server.model.Employee;

@Provider
@Consumes(MediaType.APPLICATION_XML)
public class XmlEmployeeReader implements MessageBodyReader<Employee> {

    @Override
    public boolean isReadable(Class<?> type, Type genericType, Annotation[] annotations, MediaType mediaType) {
        return (type.equals(Employee.class) && mediaType.toString().startsWith(MediaType.APPLICATION_XML.toString()));
    }

    @Override
    public Employee readFrom(Class<Employee> type, Type genericType, Annotation[] annotations, MediaType mediaType, MultivaluedMap<String, String> httpHeaders, InputStream entityStream) throws IOException, WebApplicationException {
        return JAXB.unmarshal(entityStream, type);
    }
}
