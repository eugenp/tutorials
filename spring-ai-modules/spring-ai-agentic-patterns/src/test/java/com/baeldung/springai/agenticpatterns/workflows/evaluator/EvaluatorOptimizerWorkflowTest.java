package com.baeldung.springai.agenticpatterns.workflows.evaluator;

import static com.baeldung.springai.agenticpatterns.aimodels.CodeReviewClientPrompts.CODE_REVIEW_PROMPT;
import static com.baeldung.springai.agenticpatterns.aimodels.CodeReviewClientPrompts.EVALUATE_PROPOSED_IMPROVEMENTS_PROMPT;
import static com.baeldung.springai.agenticpatterns.workflows.evaluator.EvaluatorOptimizerWorkflow.mapClass;
import static org.assertj.core.api.Assertions.assertThat;
import static org.mockito.Mockito.mock;
import static org.mockito.Mockito.when;

import java.util.Map;

import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.junit.jupiter.MockitoExtension;
import org.springframework.ai.chat.client.DefaultChatClient;

import com.baeldung.springai.agenticpatterns.aimodels.CodeReviewClient;

@ExtendWith(MockitoExtension.class)
class EvaluatorOptimizerWorkflowTest {

    @Mock
    private CodeReviewClient codeReviewClient;
    @InjectMocks
    private EvaluatorOptimizerWorkflow evaluatorOptimizerWorkflow;

    @Test
    void opsPipeline_whenAllStepsAreSuccessful_thenSuccess() {
        String prLink = "https://github.com/org/repo/pull/70";
        String firstGenerationRequest = CODE_REVIEW_PROMPT + "\n PR: " + prLink + "\n previous suggestions: {}" + "\n evaluation on previous suggestions: ";
        Map<String, String> firstSuggestion = Map.of("Client:23:'no multiple variables in 1 line'", "int x = 0;\\n int y = 0;");
        mockCodeReviewClient(firstGenerationRequest, firstSuggestion);
        String firstEvaluationRequest = EVALUATE_PROPOSED_IMPROVEMENTS_PROMPT + "\n PR: " + prLink + "\n proposed suggestions: " + firstSuggestion;
        Map<String, String> firstEvaluation = Map.of("FAIL", "method names should be more descriptive");
        mockCodeReviewClient(firstEvaluationRequest, firstEvaluation);
        String secondGenerationRequest =
          CODE_REVIEW_PROMPT + "\n PR: " + prLink + "\n previous suggestions: " + firstSuggestion + "\n evaluation on previous suggestions: " +
            firstEvaluation.values()
              .iterator()
              .next();
        Map<String, String> secondSuggestion = Map.of("Client:23:'no multiple variables in 1 line & improved names'",
          "int readTimeout = 0;\\n int connectTimeout = 0;");
        mockCodeReviewClient(secondGenerationRequest, secondSuggestion);
        String secondEvaluationRequest = EVALUATE_PROPOSED_IMPROVEMENTS_PROMPT + "\n PR: " + prLink + "\n proposed suggestions: " + secondSuggestion;
        Map<String, String> secondEvaluation = Map.of("PASS", "");
        mockCodeReviewClient(secondEvaluationRequest, secondEvaluation);

        Map<String, String> response = evaluatorOptimizerWorkflow.evaluate(prLink);

        assertThat(response).isEqualTo(secondSuggestion);
    }

    private void mockCodeReviewClient(String prompt, Map<String, String> response) {
        DefaultChatClient.DefaultChatClientRequestSpec requestSpec = mock(DefaultChatClient.DefaultChatClientRequestSpec.class);
        DefaultChatClient.DefaultCallResponseSpec responseSpec = mock(DefaultChatClient.DefaultCallResponseSpec.class);

        when(codeReviewClient.prompt(prompt)).thenReturn(requestSpec);
        when(requestSpec.call()).thenReturn(responseSpec);
        when(responseSpec.entity(mapClass)).thenReturn(response);
    }
}
