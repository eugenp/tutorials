package com.baeldung.restdocopenapi.springdoc;

import static org.springframework.hateoas.server.mvc.WebMvcLinkBuilder.linkTo;

import java.util.List;
import java.util.Optional;

import javax.validation.Valid;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpHeaders;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.DeleteMapping;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.PutMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import com.baeldung.restdocopenapi.Foo;
import com.baeldung.restdocopenapi.FooRepository;

import io.swagger.v3.oas.annotations.Operation;
import io.swagger.v3.oas.annotations.Parameter;
import io.swagger.v3.oas.annotations.media.ArraySchema;
import io.swagger.v3.oas.annotations.media.Content;
import io.swagger.v3.oas.annotations.media.Schema;
import io.swagger.v3.oas.annotations.responses.ApiResponse;
import io.swagger.v3.oas.annotations.responses.ApiResponses;
import io.swagger.v3.oas.annotations.tags.Tag;

@RestController
@RequestMapping("/foobar")
@Tag(name = "foobar", description = "the foobar API with documentation annotations")
public class FooBarController {

    @Autowired
    FooRepository repository;

    @Operation(summary = "Get all foos")
    @ApiResponses(value = { 
            @ApiResponse(responseCode = "200", description = "found foos", content = { 
                @Content(mediaType = "application/json", array = @ArraySchema(schema = @Schema(implementation = Foo.class)))}), 
            @ApiResponse(responseCode = "404", description = "No Foos found", content = @Content) })
    @GetMapping
    public ResponseEntity<List<Foo>> getAllFoos() {
        List<Foo> fooList = (List<Foo>) repository.findAll();
        if (fooList.isEmpty()) {
            return new ResponseEntity<>(HttpStatus.NOT_FOUND);
        }
        return new ResponseEntity<>(fooList, HttpStatus.OK);
    }

    @Operation(summary = "Get a foo by foo id")
    @ApiResponses(value = {
            @ApiResponse(responseCode = "200", description = "found the foo", content = { 
                    @Content(mediaType = "application/json", schema = @Schema(implementation = Foo.class))}),
            @ApiResponse(responseCode = "400", description = "Invalid id supplied", content = @Content), 
            @ApiResponse(responseCode = "404", description = "Foo not found", content = @Content) })
    @GetMapping(value = "{id}")
    public ResponseEntity<Foo> getFooById(@Parameter(description = "id of foo to be searched") @PathVariable("id") String id) {

        Optional<Foo> foo = repository.findById(Long.valueOf(id));
        return foo.isPresent() ? new ResponseEntity<>(foo.get(), HttpStatus.OK) : new ResponseEntity<>(HttpStatus.NOT_FOUND);
    }

    @Operation(summary = "Create a foo")
    @ApiResponses(value = {
            @ApiResponse(responseCode = "201", description = "foo created", content = { @
                    Content(mediaType = "application/json", schema = @Schema(implementation = Foo.class))}),
            @ApiResponse(responseCode = "404", description = "Bad request", content = @Content) })
    @PostMapping
    public ResponseEntity<Foo> addFoo(@Parameter(description = "foo object to be created") @RequestBody @Valid Foo foo) {
        HttpHeaders httpHeaders = new HttpHeaders();
        httpHeaders.setLocation(linkTo(FooBarController.class).slash(foo.getId()).toUri());
        Foo savedFoo;
        try {
            savedFoo = repository.save(foo);
        } catch (Exception e) {
            return new ResponseEntity<>(HttpStatus.BAD_REQUEST);
        }

        return new ResponseEntity<>(savedFoo, httpHeaders, HttpStatus.CREATED);
    }

    @Operation(summary = "Delete a foo")
    @ApiResponses(value = {
            @ApiResponse(responseCode = "204", description = "foo deleted"),
            @ApiResponse(responseCode = "404", description = "Bad request", content = @Content) })
    @DeleteMapping("/{id}")
    public ResponseEntity<Void> deleteFoo(@Parameter(description = "id of foo to be deleted") @PathVariable("id") long id) {
        try {
            repository.deleteById(id);
        } catch (Exception e) {
            return new ResponseEntity<>(HttpStatus.BAD_REQUEST);
        }
        return new ResponseEntity<>(HttpStatus.NO_CONTENT);
    }

    @Operation(summary = "Update a foo")
    @ApiResponses(value = {
            @ApiResponse(responseCode = "200", description = "foo updated successfully", content = {
                    @Content(mediaType = "application/json", schema = @Schema(implementation = Foo.class))}),
            @ApiResponse(responseCode = "404", description = "No Foo exists with given id", content = @Content) })
    @PutMapping("/{id}")
    public ResponseEntity<Foo> updateFoo(@Parameter(description = "id of foo to be updated") @PathVariable("id") long id, @RequestBody Foo foo) {
        
        boolean isFooPresent = repository.existsById(Long.valueOf(id));

        if (!isFooPresent) {
            return new ResponseEntity<>(HttpStatus.NOT_FOUND);
        }
        
        Foo updatedFoo = repository.save(foo);

        return new ResponseEntity<>(updatedFoo, HttpStatus.OK);
    }
}
