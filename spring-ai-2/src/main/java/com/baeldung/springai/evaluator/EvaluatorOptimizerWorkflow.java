package com.baeldung.springai.evaluator;

import org.springframework.ai.chat.model.ChatResponse;
import org.springframework.ai.chat.prompt.Prompt;
import org.springframework.ai.chat.prompt.PromptTemplate;
import org.springframework.ai.evaluation.EvaluationResponse;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.core.io.Resource;
import org.springframework.stereotype.Component;

import java.util.HashMap;
import java.util.Map;

@Component
public class EvaluatorOptimizerWorkflow {

    private static final int MAX_ITERATIONS = 3;
    private static final float ACCEPTABLE_SCORE = 0.8f;

    private final ChatbotService chatbotService;
    private final LLMResponseEvaluator llmResponseEvaluator;
    private final Resource optimizationFeebackPrompt;

    public EvaluatorOptimizerWorkflow(
        ChatbotService chatbotService,
        LLMResponseEvaluator llmResponseEvaluator,
        @Value("classpath:prompts/optimization-feedback-prompt.st") Resource optimizationFeebackPrompt) {
        this.chatbotService = chatbotService;
        this.llmResponseEvaluator = llmResponseEvaluator;
        this.optimizationFeebackPrompt = optimizationFeebackPrompt;
    }

    public String execute(String question) {
        ChatResponse currentResponse = null;
        EvaluationResponse evaluationResponse = null;
        int iterations = 0;

        do {
            Prompt prompt = constructUserPrompt(question, evaluationResponse, currentResponse);
            currentResponse = chatbotService.chat(prompt);
            evaluationResponse = llmResponseEvaluator.evaluate(prompt, currentResponse);
            iterations++;
        } while (shouldContinueOptimization(evaluationResponse, iterations));

        return currentResponse.getResult().getOutput().getContent();
    }

    private Prompt constructUserPrompt(String question, EvaluationResponse previousEvaluation, ChatResponse currentResponse) {
        if (previousEvaluation == null) {
            return new Prompt(question);
        }
        PromptTemplate promptTemplate = new PromptTemplate(optimizationFeebackPrompt);
        Map<String, Object> templateVariables = new HashMap<>();
        templateVariables.put("question", question);
        templateVariables.put("previousAnswer", currentResponse.getResult().getOutput().getContent());
        templateVariables.put("feedback", previousEvaluation.getFeedback());

        return promptTemplate.create(templateVariables);
    }

    private boolean shouldContinueOptimization(EvaluationResponse evaluationResponse, int iterations) {
        return !evaluationResponse.isPass()
            && evaluationResponse.getScore() < ACCEPTABLE_SCORE
            && iterations < MAX_ITERATIONS;
    }

}