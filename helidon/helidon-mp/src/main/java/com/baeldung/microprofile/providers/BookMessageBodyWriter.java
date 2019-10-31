package com.baeldung.microprofile.providers;

import com.baeldung.microprofile.model.Book;
import com.baeldung.microprofile.util.BookMapper;

import javax.json.Json;
import javax.json.JsonObject;
import javax.json.JsonWriter;
import javax.ws.rs.Produces;
import javax.ws.rs.WebApplicationException;
import javax.ws.rs.core.MediaType;
import javax.ws.rs.core.MultivaluedMap;
import javax.ws.rs.ext.MessageBodyWriter;
import javax.ws.rs.ext.Provider;
import java.io.IOException;
import java.io.OutputStream;
import java.lang.annotation.Annotation;
import java.lang.reflect.Type;

@Provider
@Produces(MediaType.APPLICATION_JSON)
public class BookMessageBodyWriter implements MessageBodyWriter<Book> {
    @Override
    public boolean isWriteable(Class<?> type, Type genericType, Annotation[] annotations, MediaType mediaType) {
        return type.equals(Book.class);
    }

    /*
    Deprecated in JAX RS 2.0
     */
    @Override
    public long getSize(Book book, Class<?> type, Type genericType, Annotation[] annotations, MediaType mediaType) {
        return 0;
    }

    /**
     * Marsahl Book to OutputStream
     *
     * @param book
     * @param type
     * @param genericType
     * @param annotations
     * @param mediaType
     * @param httpHeaders
     * @param entityStream
     * @throws IOException
     * @throws WebApplicationException
     */
    @Override
    public void writeTo(Book book, Class<?> type, Type genericType, Annotation[] annotations, MediaType mediaType, MultivaluedMap<String, Object> httpHeaders, OutputStream entityStream) throws IOException, WebApplicationException {
        JsonWriter jsonWriter = Json.createWriter(entityStream);
        JsonObject jsonObject = BookMapper.map(book);
        jsonWriter.writeObject(jsonObject);
        jsonWriter.close();
    }

}
