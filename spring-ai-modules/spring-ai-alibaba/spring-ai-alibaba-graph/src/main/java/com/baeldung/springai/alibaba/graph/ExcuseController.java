package com.baeldung.springai.alibaba.graph;

import java.util.List;
import java.util.Map;
import java.util.UUID;

import com.alibaba.cloud.ai.graph.RunnableConfig;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RestController;

import com.alibaba.cloud.ai.graph.CompiledGraph;
import com.alibaba.cloud.ai.graph.OverAllState;

@RestController
class ExcuseController {

    private final CompiledGraph excuseGraph;

    ExcuseController(CompiledGraph excuseGraph) {
        this.excuseGraph = excuseGraph;
    }

    @PostMapping("/excuse")
    ResponseEntity<ExcuseResponse> generateExcuse(@RequestBody ExcuseRequest request) {
        OverAllState finalState = excuseGraph
            .invoke(
                Map.of("situation", request.situation()),
                RunnableConfig.builder()
                    .threadId(UUID.randomUUID().toString())
                    .build())
            .orElseThrow();

        ExcuseResponse response = new ExcuseResponse(
            finalState.value("excuse", ""),
            finalState.value("managerReplies", List.of()),
            finalState.value("attempts", 0),
            ExcuseDispatcher.isBelieved(finalState.value("believability", 0.0)));

        return ResponseEntity.ok(response);
    }

    record ExcuseRequest(String situation) {}

    record ExcuseResponse(
        String finalExcuse,
        List<String> managerReplies,
        Integer attempts,
        boolean believed
    ) {}
}