package com.baeldung.springai.evaluator;

import org.springframework.ai.chat.client.advisor.QuestionAnswerAdvisor;
import org.springframework.ai.chat.model.ChatResponse;
import org.springframework.ai.chat.prompt.Prompt;
import org.springframework.ai.document.Document;
import org.springframework.ai.evaluation.EvaluationRequest;
import org.springframework.ai.evaluation.EvaluationResponse;
import org.springframework.ai.evaluation.FactCheckingEvaluator;
import org.springframework.stereotype.Component;

import java.util.List;

@Component
public class LLMResponseEvaluator {

    private final FactCheckingEvaluator factCheckingEvaluator;

    public LLMResponseEvaluator(FactCheckingEvaluator factCheckingEvaluator) {
        this.factCheckingEvaluator = factCheckingEvaluator;
    }

    public EvaluationResponse evaluate(Prompt question, ChatResponse chatResponse) {
        String answer = chatResponse.getResult().getOutput().getContent();
        List<Document> documents = chatResponse.getMetadata().get(QuestionAnswerAdvisor.RETRIEVED_DOCUMENTS);
        EvaluationRequest evaluationRequest = new EvaluationRequest(question.getContents(), documents, answer);
        return factCheckingEvaluator.evaluate(evaluationRequest);
    }

}