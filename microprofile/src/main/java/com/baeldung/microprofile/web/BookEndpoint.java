package com.baeldung.microprofile.web;

import com.baeldung.microprofile.model.Book;
import com.baeldung.microprofile.repo.BookManager;
import com.baeldung.microprofile.util.Utils;

import javax.inject.Inject;
import javax.json.*;
import javax.ws.rs.*;
import javax.ws.rs.core.MediaType;
import java.util.List;

@Path("books")
@Produces(MediaType.APPLICATION_JSON)
@Consumes(MediaType.APPLICATION_JSON)
public class BookEndpoint {

    @Inject
    private BookManager bookManager;

    @POST
    public JsonObject add(JsonObject jsonObject) {
        Book book = Utils.map(jsonObject);
        String bookId = bookManager.add(book);
        return Json.createObjectBuilder().add("id", bookId).build();
    }

    @GET
    @Path("{id}")
    public JsonObject getBook(@PathParam("id") String id) {
        Book book = bookManager.get(id);
        return Utils.map(book);
    }

    @GET
    public List<Book> getAllBooks() {
        JsonArrayBuilder books = Json.createArrayBuilder();

        bookManager.list().forEach(book -> {
            books.add(Utils.map(book));
        });
        return null;
    }

    @DELETE
    public void deleteAll() {
        bookManager.deleteAll();
    }

}