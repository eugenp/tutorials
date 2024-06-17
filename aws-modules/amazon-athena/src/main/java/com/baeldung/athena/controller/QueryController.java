package com.baeldung.athena.controller;

import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import com.baeldung.athena.service.QueryService;

import lombok.NonNull;
import lombok.RequiredArgsConstructor;

@RestController
@RequiredArgsConstructor
@RequestMapping("/api/v1/query")
public class QueryController {

    private final QueryService queryService;

    @PostMapping
    public ResponseEntity<?> executeQuery(@RequestBody final QueryRequest request) {
        final var response = queryService.execute(request.sqlQuery);
        return ResponseEntity.ok(response);
    }

    public record QueryRequest(@NonNull String sqlQuery) {}

}
