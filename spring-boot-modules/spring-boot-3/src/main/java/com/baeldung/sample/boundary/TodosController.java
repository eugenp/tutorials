package com.baeldung.sample.boundary;

import com.baeldung.sample.control.NotFoundException;
import com.baeldung.sample.control.TodosService;
import jakarta.validation.Valid;
import lombok.RequiredArgsConstructor;
import org.springframework.http.MediaType;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.DeleteMapping;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.PutMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.ResponseStatus;
import org.springframework.web.bind.annotation.RestController;

import java.net.URI;
import java.util.Collection;
import java.util.List;
import java.util.stream.Collectors;

import static org.springframework.hateoas.server.mvc.WebMvcLinkBuilder.linkTo;
import static org.springframework.hateoas.server.mvc.WebMvcLinkBuilder.methodOn;
import static org.springframework.http.HttpStatus.NO_CONTENT;

@RestController
@RequestMapping("/api/v1/todos")
@RequiredArgsConstructor
public class TodosController {

    private static final String DEFAULT_MEDIA_TYPE = MediaType.APPLICATION_JSON_VALUE;

    private final TodosService service;
    // Mapping zwischen den Schichten
    private final TodoDtoMapper mapper;


    @GetMapping(value = {"/name"},produces = DEFAULT_MEDIA_TYPE)
    public List<String> findAllName(){
        return List.of("Hello", "World");
    }


    @GetMapping(produces = DEFAULT_MEDIA_TYPE)
    public Collection<TodoResponseDto> findAll() {
        return service.findAll().stream()
          .map(mapper::map)
          .collect(Collectors.toList());
    }

    @GetMapping(value = "/{id}", produces = DEFAULT_MEDIA_TYPE)
    public TodoResponseDto findById(
      @PathVariable("id") final Long id
    ) {
        // Action
        return service.findById(id) //
          .map(mapper::map) // map to dto
          .orElseThrow(NotFoundException::new);
    }

    @PostMapping(consumes = DEFAULT_MEDIA_TYPE)
    public ResponseEntity<TodoResponseDto> create(final @Valid @RequestBody TodoRequestDto item) {
        // Action
        final var todo = mapper.map(item, null);
        final var newTodo = service.create(todo);
        final var result = mapper.map(newTodo);
        // Response
        final URI locationHeader = linkTo(methodOn(TodosController.class).findById(result.getId())).toUri(); // HATEOAS
        return ResponseEntity.created(locationHeader).body(result);
    }

    @PutMapping(value = "{id}", consumes = DEFAULT_MEDIA_TYPE)
    @ResponseStatus(NO_CONTENT)
    public void update(
      @PathVariable("id") final Long id,
      @Valid @RequestBody final TodoRequestDto item
    ) {
        service.update(mapper.map(item, id));
    }

    @DeleteMapping("/{id}")
    @ResponseStatus(NO_CONTENT)
    public void delete(
      @PathVariable("id") final Long id
    ) {
        service.delete(id);
    }

}