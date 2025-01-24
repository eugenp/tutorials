package com.baeldung.springai.evaluator;

import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

@RestController
@RequestMapping("/api/v1")
public class ChatbotController {

    private final EvaluatorOptimizerWorkflow evaluatorOptimizerWorkflow;

    public ChatbotController(EvaluatorOptimizerWorkflow evaluatorOptimizerWorkflow) {
        this.evaluatorOptimizerWorkflow = evaluatorOptimizerWorkflow;
    }

    @PostMapping("/chat")
    public ResponseEntity<ChatResponse> chat(@RequestBody ChatRequest chatRequest) {
        String answer = evaluatorOptimizerWorkflow.process(chatRequest.question);
        return ResponseEntity.ok(new ChatResponse(answer));
    }

    record ChatRequest(String question) {}

    record ChatResponse(String answer) {}

}