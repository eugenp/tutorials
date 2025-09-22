package com.baeldung.springai.agenticpatterns.workflows.evaluator;

import static com.baeldung.springai.agenticpatterns.aimodels.CodeReviewClientPrompts.CODE_REVIEW_PROMPT;
import static com.baeldung.springai.agenticpatterns.aimodels.CodeReviewClientPrompts.EVALUATE_PROPOSED_IMPROVEMENTS_PROMPT;

import java.util.HashMap;
import java.util.Map;

import org.springframework.ai.chat.client.ChatClient;
import org.springframework.core.ParameterizedTypeReference;
import org.springframework.stereotype.Component;

import com.baeldung.springai.agenticpatterns.aimodels.CodeReviewClient;

@Component
public class EvaluatorOptimizerWorkflow {

    private final CodeReviewClient codeReviewClient;
    static final ParameterizedTypeReference<Map<String, String>> mapClass = new ParameterizedTypeReference<>() {};

    public EvaluatorOptimizerWorkflow(CodeReviewClient codeReviewClient) {
        this.codeReviewClient = codeReviewClient;
    }

    public Map<String, String> evaluate(String task) {
        return loop(task, new HashMap<>(), "");
    }

    private Map<String, String> loop(String task, Map<String, String> latestSuggestions, String evaluation) {
        latestSuggestions = generate(task, latestSuggestions, evaluation);
        Map<String, String> evaluationResponse = evaluate(latestSuggestions, task);
        String outcome = evaluationResponse.keySet().iterator().next();
        evaluation = evaluationResponse.values().iterator().next();

        if ("PASS".equals(outcome)) {
            System.out.println("Accepted RE Review Suggestions:\n" + latestSuggestions);
            return latestSuggestions;
        }

        return loop(task, latestSuggestions, evaluation);
    }

    private Map<String, String> generate(String task, Map<String, String> previousSuggestions, String evaluation) {
        String request = CODE_REVIEW_PROMPT +
          "\n PR: " + task +
          "\n previous suggestions: " + previousSuggestions +
          "\n evaluation on previous suggestions: " + evaluation;
        System.out.println("PR REVIEW PROMPT: " + request);

        ChatClient.ChatClientRequestSpec requestSpec = codeReviewClient.prompt(request);
        ChatClient.CallResponseSpec responseSpec = requestSpec.call();
        Map<String, String> response = responseSpec.entity(mapClass);

        System.out.println("PR REVIEW OUTCOME: " + response);

        return response;
    }

    private Map<String, String> evaluate(Map<String, String> latestSuggestions, String task) {
        String request = EVALUATE_PROPOSED_IMPROVEMENTS_PROMPT +
          "\n PR: " + task +
          "\n proposed suggestions: " + latestSuggestions;
        System.out.println("EVALUATION PROMPT: " + request);

        ChatClient.ChatClientRequestSpec requestSpec = codeReviewClient.prompt(request);
        ChatClient.CallResponseSpec responseSpec = requestSpec.call();
        Map<String, String> response = responseSpec.entity(mapClass);
        System.out.println("EVALUATION OUTCOME: " + response);

        return response;
    }
}
