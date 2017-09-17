package com.baeldung.couchbase.n1ql;

import com.couchbase.client.java.query.N1qlQueryResult;
import com.fasterxml.jackson.databind.JsonNode;
import com.fasterxml.jackson.databind.ObjectMapper;
import io.vavr.control.Try;

import java.io.IOException;
import java.util.List;
import java.util.Objects;
import java.util.stream.Collectors;

public class CodeSnippets {

    private static ObjectMapper objectMapper = new ObjectMapper();

    public static List<JsonNode> extractJsonResult(N1qlQueryResult result) {
      return result.allRows().stream()
        .map(row -> {
            try {
              return objectMapper.readTree(row.value().toString());
            }catch (IOException e) {
               e.printStackTrace();
               return null;
            }
        })
        .filter(Objects::nonNull)
        .collect(Collectors.toList());
    }

}
