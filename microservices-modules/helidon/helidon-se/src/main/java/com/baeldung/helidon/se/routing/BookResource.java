package com.baeldung.helidon.se.routing;

import io.helidon.webserver.Routing;
import io.helidon.webserver.ServerRequest;
import io.helidon.webserver.ServerResponse;
import io.helidon.webserver.Service;

import javax.json.Json;
import javax.json.JsonArray;
import javax.json.JsonArrayBuilder;
import javax.json.JsonObject;
import java.util.List;

public class BookResource implements Service {

    private BookManager bookManager = new BookManager();

    @Override
    public void update(Routing.Rules rules) {
        rules
                .get("/", this::books)
                .get("/{id}", this::bookById);
    }

    private void bookById(ServerRequest serverRequest, ServerResponse serverResponse) {
        //get the book with the given id
        String id = serverRequest.path().param("id");
        Book book = bookManager.get(id);
        JsonObject jsonObject = from(book);
        serverResponse.send(jsonObject);
    }

    private void books(ServerRequest serverRequest, ServerResponse serverResponse) {
        //get all books
        List<Book> books = bookManager.getAll();
        JsonArray jsonArray = from(books);
        serverResponse.send(jsonArray);
    }

    private JsonObject from(Book book) {
        JsonObject jsonObject = Json.createObjectBuilder()
                .add("id", book.getId())
                .add("isbn", book.getIsbn())
                .add("name", book.getName())
                .add("author", book.getAuthor())
                .add("pages", book.getPages())
                .build();
        return jsonObject;
    }

    private JsonArray from(List<Book> books) {
        JsonArrayBuilder jsonArrayBuilder = Json.createArrayBuilder();
        books.forEach(book -> {
            jsonArrayBuilder.add(from(book));
        });
        return jsonArrayBuilder.build();
    }
}
