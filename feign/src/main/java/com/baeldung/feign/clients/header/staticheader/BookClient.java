package com.baeldung.feign.clients.header.staticheader;

import com.baeldung.feign.models.Book;
import com.baeldung.feign.models.BookResource;
import feign.Headers;
import feign.Param;
import feign.RequestLine;

@Headers("Accept-Language: en-US")
public interface BookClient {
    
    @RequestLine("GET /{isbn}")
    BookResource findByIsbn(@Param("isbn") String isbn);

    @RequestLine("POST")
    @Headers("Content-Type: application/json")
    void create(Book book);
}
