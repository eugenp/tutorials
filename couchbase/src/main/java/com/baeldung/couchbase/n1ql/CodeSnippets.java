package com.baeldung.couchbase.n1ql;

import com.couchbase.client.java.query.N1qlQueryResult;
import com.fasterxml.jackson.databind.JsonNode;
import com.fasterxml.jackson.databind.ObjectMapper;

import java.io.IOException;
import java.util.List;
import java.util.Objects;
import java.util.logging.Level;
import java.util.logging.Logger;
import java.util.stream.Collectors;

public class CodeSnippets {

    private static ObjectMapper objectMapper = new ObjectMapper();

    private static final Logger logger = Logger.getLogger(CodeSnippets.class.getName());

    public static List<JsonNode> extractJsonResult(N1qlQueryResult result) {
      return result.allRows().stream()
        .map(row -> {
            try {
              return objectMapper.readTree(row.value().toString());
            }catch (IOException e) {
               logger.log(Level.WARNING, e.getLocalizedMessage());
               return null;
            }
        })
        .filter(Objects::nonNull)
        .collect(Collectors.toList());
    }

}
