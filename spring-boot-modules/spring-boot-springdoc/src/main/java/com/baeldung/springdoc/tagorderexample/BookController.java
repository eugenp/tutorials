package com.baeldung.springdoc.tagorderexample;

import java.util.ArrayList;
import java.util.List;
 
import jakarta.validation.constraints.NotBlank;
import jakarta.validation.constraints.NotNull;
import jakarta.validation.constraints.Size;
import org.springframework.validation.annotation.Valid;
import org.springframework.validation.annotation.Validated;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;
import io.swagger.v3.oas.annotations.media.Content;
import io.swagger.v3.oas.annotations.tags.Tag;
import io.swagger.v3.oas.annotations.OpenAPIDefinition;


@RestController
@Validated
/**@OpenAPIDefinition(tags = {
     @Tag(name = "books", description = "Books related tag"),
     @Tag(name = "create", description = "Add book to inventory"),
     @Tag(name = "createBook", description = "Add book to inventory"),
     @Tag(name = "delete", description = "Delete book from inventory"),
     @Tag(name = "deleteBook", description = "Delete book from inventory"),
     @Tag(name = "find", description = "Find book from inventory"),
     @Tag(name = "findBook", description = "Find book from inventory"),
     @Tag(name = "update", description = "Update book in inventory"),
     @Tag(name = "updateBook", description = "Update book in inventory")
})**/
@Tag(name = "books", description = "Books related tag")
public class BookController {

    @Tag(name = "create")
    @Tag(name = "books")
    @Tag(name = "createBook")
    @RequestMapping(path = "/book", method = RequestMethod.POST)
    @io.swagger.v3.oas.annotations.parameters.RequestBody(required = true)
    public Book book(@Valid @RequestBody Book book) {
        return book;
    }

    @Tag(name = "find")
    @Tag(name = "books")
    @Tag(name = "findBook")
    @RequestMapping(path = "/findBookById", method = RequestMethod.GET)
    public List<Book> findById(@RequestParam(name = "id", required = true) @NotNull @NotBlank @Size(max = 10) int id) {
        List<Book> bookList = new ArrayList<>();
	Book book = new Book();
	book.setId(1);
	bookList.add(book);
	return bookList;
    }

    @Tag(name = "delete")
    @Tag(name = "books")
    @Tag(name = "deleteBook")
    @RequestMapping(path = "/deleteBookById", method = RequestMethod.DELETE)
    public long deleteById(@RequestParam(name = "id", required = true) @NotNull @NotBlank @Size(max = 10) long id) {
        return id;
    }

    @Tag(name = "update")
    @Tag(name = "books")
    @Tag(name = "updateBook")
    @RequestMapping(path = "/updateBookById", method = RequestMethod.PUT)
    public long updateById(@RequestParam(name = "id", required = true) @NotNull @NotBlank @Size(max = 10) long id) {
        return id;
    }	 
}
