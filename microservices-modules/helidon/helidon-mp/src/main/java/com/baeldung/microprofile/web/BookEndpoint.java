package com.baeldung.microprofile.web;

import com.baeldung.microprofile.model.Book;
import com.baeldung.microprofile.repo.BookManager;

import jakarta.enterprise.context.RequestScoped;
import jakarta.inject.Inject;

import jakarta.ws.rs.Consumes;
import jakarta.ws.rs.GET;
import jakarta.ws.rs.POST;
import jakarta.ws.rs.Path;
import jakarta.ws.rs.PathParam;
import jakarta.ws.rs.Produces;
import jakarta.ws.rs.core.MediaType;
import jakarta.ws.rs.core.Response;
import jakarta.ws.rs.core.UriBuilder;

@Path("books")
@RequestScoped
public class BookEndpoint {

    @Inject
    private BookManager bookManager;

    @GET
    @Path("{id}")
    @Produces(MediaType.APPLICATION_JSON)
    public Response getBook(@PathParam("id") String id) {
        Book book = bookManager.get(id);
        return Response.ok(book).build();
    }

    @GET
    @Produces(MediaType.APPLICATION_JSON)
    public Response getAllBooks() {
        return Response.ok(bookManager.getAll()).build();
    }

    @POST
    @Consumes(MediaType.APPLICATION_JSON)
    public Response add(Book book) {
        String bookId = bookManager.add(book);
        return Response.created(
                UriBuilder.fromResource(this.getClass()).path(bookId).build())
                .build();
    }
}
