package com.baeldung.cats.controller;

import java.util.List;

import org.springframework.http.MediaType;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.DeleteMapping;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import com.baeldung.cats.model.BadApiRequest;
import com.baeldung.cats.model.Item;
import com.baeldung.cats.service.ItemService;

import io.swagger.v3.oas.annotations.media.ArraySchema;
import io.swagger.v3.oas.annotations.media.Content;
import io.swagger.v3.oas.annotations.media.Schema;
import io.swagger.v3.oas.annotations.responses.ApiResponse;
import jakarta.persistence.EntityNotFoundException;
import jakarta.validation.Valid;

@RestController
@RequestMapping("/api/v2/item")
@ApiResponse(responseCode = "401", description = "Unauthorized", content = { @Content(mediaType = MediaType.TEXT_PLAIN_VALUE, schema = @Schema(implementation = String.class)) })
@ApiResponse(responseCode = "400", description = "Bad Request", content = { @Content(mediaType = MediaType.APPLICATION_JSON_VALUE, schema = @Schema(implementation = BadApiRequest.class)) })
public class ItemController {

    private ItemService service;

    public ItemController(ItemService service) {
        this.service = service;
    }

    @PostMapping
    @ApiResponse(responseCode = "200", description = "Success", content = { @Content(mediaType = MediaType.APPLICATION_JSON_VALUE, schema = @Schema(implementation = Item.class)) })
    public ResponseEntity<Item> post(@Valid @RequestBody Item item) {
        service.insert(item);
        return ResponseEntity.ok(item);
    }

    @GetMapping
    @ApiResponse(responseCode = "200", description = "Success", content = { @Content(mediaType = MediaType.APPLICATION_JSON_VALUE, array = @ArraySchema(schema = @Schema(implementation = Item.class))) })
    public ResponseEntity<List<Item>> get() {
        return ResponseEntity.ok(service.findAll());
    }

    @GetMapping("/{id}")
    @ApiResponse(responseCode = "200", description = "Success", content = { @Content(mediaType = MediaType.APPLICATION_JSON_VALUE, schema = @Schema(implementation = Item.class)) })
    public ResponseEntity<Item> get(@PathVariable String id) {
        try {
            return ResponseEntity.ok(service.findById(id));
        } catch (EntityNotFoundException e) {
            return ResponseEntity.status(404)
                .build();
        }
    }

    @DeleteMapping("/{id}")
    @ApiResponse(responseCode = "204", description = "Success")
    public ResponseEntity<Void> delete(@PathVariable String id) {
        try {
            service.deleteById(id);

            return ResponseEntity.status(204)
                .build();
        } catch (EntityNotFoundException e) {
            return ResponseEntity.status(404)
                .build();
        }
    }
}
