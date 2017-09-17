package com.baeldung.couchbase.n1ql;

import com.couchbase.client.java.query.N1qlQueryResult;
import com.fasterxml.jackson.databind.JsonNode;
import com.fasterxml.jackson.databind.ObjectMapper;
import io.vavr.control.Try;

import java.util.List;
import java.util.stream.Collectors;

public class CodeSnippets {

    public static List<JsonNode> extractJsonResult(N1qlQueryResult result) {
      ObjectMapper objectMapper = new ObjectMapper();
      return result.allRows().stream()
        .map(row -> Try.of(() -> objectMapper.readTree(row.value().toString()))
           .getOrNull())
        .collect(Collectors.toList());
    }

}
