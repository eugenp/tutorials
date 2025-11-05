package com.baeldung.quarkus;

import com.baeldung.quarkus.model.Book;
import com.baeldung.quarkus.service.LibraryService;

import jakarta.inject.Inject;
import jakarta.ws.rs.*;
import jakarta.ws.rs.core.MediaType;
import java.util.Set;

@Path("/library")
@Produces(MediaType.APPLICATION_JSON)
@Consumes(MediaType.APPLICATION_JSON)
public class LibraryResource {

    @Inject
    LibraryService libraryService;

    @GET
    @Path("/book")
    public Set<Book> findBooks(@QueryParam("query") String query) {
        return libraryService.find(query);
    }

}
