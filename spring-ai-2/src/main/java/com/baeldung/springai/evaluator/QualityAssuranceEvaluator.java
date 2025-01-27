package com.baeldung.springai.evaluator;

import org.springframework.ai.chat.client.ChatClient;
import org.springframework.ai.chat.prompt.Prompt;
import org.springframework.ai.chat.prompt.PromptTemplate;
import org.springframework.ai.evaluation.EvaluationRequest;
import org.springframework.ai.evaluation.EvaluationResponse;
import org.springframework.ai.evaluation.Evaluator;
import org.springframework.beans.factory.annotation.Qualifier;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.core.io.Resource;
import org.springframework.stereotype.Component;

import java.util.HashMap;
import java.util.Map;

@Component
public class QualityAssuranceEvaluator implements Evaluator {

    private static final float FEEDBACK_THRESHOLD = 0.7f;

    private final ChatClient chatClient;
    private final Resource evaluationUserPrompt;
    private final Resource evaluationSystemPrompt;

    public QualityAssuranceEvaluator(
        @Qualifier("contentEvaluator") ChatClient chatClient,
        @Value("classpath:prompts/quality-assurance-evaluator-user-prompt.st") Resource evaluationUserPrompt,
        @Value("classpath:prompts/quality-assurance-evaluator-system-prompt.st") Resource evaluationSystemPrompt) {
        this.chatClient = chatClient;
        this.evaluationUserPrompt = evaluationUserPrompt;
        this.evaluationSystemPrompt = evaluationSystemPrompt;
    }

    @Override
    public EvaluationResponse evaluate(EvaluationRequest evaluationRequest) {
        Prompt userPrompt = constructUserPrompt(evaluationRequest);
        var response = chatClient
            .prompt()
            .system(evaluationSystemPrompt)
            .user(userPrompt.getContents())
            .call()
            .entity(QualityAssuranceEvaluationResponse.class);
        return new EvaluationResponse(response.pass(), response.score(), response.feedback(), null);
    }

    private Prompt constructUserPrompt(EvaluationRequest evaluationRequest) {
        String query = evaluationRequest.getUserText();
        String response = evaluationRequest.getResponseContent();
        String context = doGetSupportingData(evaluationRequest);

        PromptTemplate promptTemplate = new PromptTemplate(evaluationUserPrompt);
        Map<String, Object> templateVariables = new HashMap<>();
        templateVariables.put("query", query);
        templateVariables.put("response", response);
        templateVariables.put("context", context);

        return promptTemplate.create(templateVariables);
    }

}