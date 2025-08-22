package com.baeldung.springai.texttosql;

import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RestController;

import java.util.List;

@RestController
class QueryController {

    private final SqlExecutor sqlExecutor;
    private final SqlGenerator sqlGenerator;

    QueryController(SqlExecutor sqlExecutor, SqlGenerator sqlGenerator) {
        this.sqlExecutor = sqlExecutor;
        this.sqlGenerator = sqlGenerator;
    }

    @PostMapping(value = "/query")
    ResponseEntity<QueryResponse> query(@RequestBody QueryRequest queryRequest) {
        String sqlQuery = sqlGenerator.generate(queryRequest.question());
        List<?> result = sqlExecutor.execute(sqlQuery);
        return ResponseEntity.ok(new QueryResponse(result));
    }

    record QueryRequest(String question) {
    }

    record QueryResponse(List<?> result) {
    }

}