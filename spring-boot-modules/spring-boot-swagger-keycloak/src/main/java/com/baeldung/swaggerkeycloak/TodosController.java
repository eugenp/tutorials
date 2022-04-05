package com.baeldung.swaggerkeycloak;

import io.swagger.annotations.ApiOperation;
import io.swagger.annotations.ApiResponse;
import io.swagger.annotations.ApiResponses;
import org.springframework.http.MediaType;
import org.springframework.security.access.prepost.PreAuthorize;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import javax.annotation.PostConstruct;
import java.time.LocalDate;
import java.util.Collection;
import java.util.HashMap;
import java.util.Map;

@RestController
@RequestMapping("/api/v1/todos")
public class TodosController {

    private final Map<Long, Todo> todos = new HashMap<>();

    @PostConstruct
    public void initData() {
        todos.put(1L, new Todo(1L, "Install Keycloak", LocalDate.now().plusDays(14)));
        todos.put(2L, new Todo(2L, "Configure realm", LocalDate.now().plusDays(21)));
    }

    @GetMapping(produces = MediaType.APPLICATION_JSON_VALUE)
    @ApiOperation("Read all todos")
    @ApiResponses({
      @ApiResponse(code = 200, message = "The todos were found and returned.")
    })
    @PreAuthorize("hasAuthority('SCOPE_read_access')")
    public Collection<Todo> readAll() {
        return todos.values();
    }

}
