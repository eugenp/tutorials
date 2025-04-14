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
import io.swagger.v3.oas.annotations.OpenAPIDefinition;

@RestController
@Validated
@OpenAPIDefinition(tags = { @Tag(name = "create", description = "Add book to inventory"),
		@Tag(name = "delete", description = "Delete book from inventory"),
		@Tag(name = "find", description = "Find book from inventory"),
		@Tag(name = "update", description = "Update book in inventory"),
		@Tag(name = "createBook", description = "Add book to inventory"),
		@Tag(name = "deleteBook", description = "Delete book from inventory"),
		@Tag(name = "findBook", description = "Find book from inventory"),
		@Tag(name = "updateBook", description = "Update book in inventory") })
public class BooksController_2 {

	@Tag(name = "create")
	@Tag(name = "createBook")
	@PostMapping(path = "/addBook")
	@io.swagger.v3.oas.annotations.parameters.RequestBody(required = true)
	public Book addBook(@Valid @RequestBody Book book) {

		return book;
	}

	@Tag(name = "find")
	@Tag(name = "findBook")
	@GetMapping(path = "/findABookById")
	public List<Book> findABookById(@RequestParam(name = "id", required = true) @NotNull @NotBlank @Size(max = 10) long id) {
		List<Book> bookList = new ArrayList<>();
		Book book = new Book();

		book.setId(1);
		bookList.add(book);
		return bookList;
	}

	@Tag(name = "delete")
	@Tag(name = "deleteBook")
	@DeleteMapping(path = "/deleteABookById")
	public long deleteABookById(@RequestParam(name = "id", required = true) @NotNull @NotBlank @Size(max = 10) long id) {

		return id;
	}

	@Tag(name = "update")
	@Tag(name = "updateBook")
	@PutMapping(path = "/updateABookById")
	public long updateABookById(@RequestParam(name = "id", required = true) @NotNull @NotBlank @Size(max = 10) long id) {
		return id;
	}
}
