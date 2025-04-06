package com.baeldung.springdoc.tagorderexample;

import java.util.ArrayList;
import java.util.List;

import jakarta.validation.Valid;
import jakarta.validation.constraints.NotBlank;
import jakarta.validation.constraints.NotNull;
import jakarta.validation.constraints.Size;

import org.springframework.validation.annotation.Validated;
import org.springframework.web.bind.annotation.DeleteMapping;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.PutMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;

import io.swagger.v3.oas.annotations.tags.Tag;

@RestController
@Validated
@Tag(name = "tag_at_class_level", description = "Books related class level tag")
public class BooksController {

    @Tag(name = "create")
    @Tag(name = "common_tag_at_method_level")
    @Tag(name = "createBook")
    @PostMapping(path = "/book")
    @io.swagger.v3.oas.annotations.parameters.RequestBody(required = true)
    public Book book(@Valid @RequestBody Book book) {

        return book;
    }

    @Tag(name = "find")
    @Tag(name = "common_tag_at_method_level")
    @Tag(name = "findBook", description = "Find Books related tag")
    @GetMapping(path = "/findBookById")
    public List findById(@RequestParam(name = "id", required = true) 
      @NotNull @NotBlank @Size(max = 10) long id) {
        List bookList = new ArrayList&lt;&gt;();
	Book book = new Book();

	book.setId(1);
	bookList.add(book);
	return bookList;
    }

    @Tag(name = "delete") 
    @Tag(name = "common_tag_at_method_level")
    @Tag(name = "deleteBook")
    @DeleteMapping(path = "/deleteBookById")
    public long deleteById(@RequestParam(name = "id", required = true) 
      @NotNull @NotBlank @Size(max = 10) long id) {

        return id;
    }

    @Tag(name = "update")
    @Tag(name = "common_tag_at_method_level")
    @Tag(name = "updateBook")
    @PutMapping(path = "/updateBookById")
    public long updateById(@RequestParam(name = "id", required = true) 
      @NotNull @NotBlank @Size(max = 10) long id) {
        return id;
    }
}
