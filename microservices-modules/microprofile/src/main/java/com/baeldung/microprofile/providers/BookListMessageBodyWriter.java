package com.baeldung.microprofile.providers;

import com.baeldung.microprofile.model.Book;
import com.baeldung.microprofile.util.BookMapper;

import jakarta.json.Json;
import jakarta.json.JsonArray;
import jakarta.json.JsonWriter;
import jakarta.ws.rs.Produces;
import jakarta.ws.rs.WebApplicationException;
import jakarta.ws.rs.core.MediaType;
import jakarta.ws.rs.core.MultivaluedMap;
import jakarta.ws.rs.ext.MessageBodyWriter;
import jakarta.ws.rs.ext.Provider;
import java.io.IOException;
import java.io.OutputStream;
import java.lang.annotation.Annotation;
import java.lang.reflect.Type;
import java.util.List;

@Provider
@Produces(MediaType.APPLICATION_JSON)
public class BookListMessageBodyWriter implements MessageBodyWriter<List<Book>> {

    @Override
    public boolean isWriteable(Class<?> clazz, Type genericType, Annotation[] annotations, MediaType mediaType) {
        return true;
    }

    @Override
    public long getSize(List<Book> books, Class<?> type, Type genericType, Annotation[] annotations, MediaType mediaType) {
        return 0;
    }

    @Override
    public void writeTo(List<Book> books, Class<?> type, Type genericType, Annotation[] annotations, MediaType mediaType, MultivaluedMap<String, Object> httpHeaders, OutputStream entityStream) throws IOException, WebApplicationException {
        JsonWriter jsonWriter = Json.createWriter(entityStream);
        JsonArray jsonArray = BookMapper.map(books);
        jsonWriter.writeArray(jsonArray);
        jsonWriter.close();
    }
}
