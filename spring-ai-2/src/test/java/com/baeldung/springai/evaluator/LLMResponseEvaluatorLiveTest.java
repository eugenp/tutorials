package com.baeldung.springai.evaluator;

import org.junit.jupiter.api.Test;
import org.springframework.ai.chat.client.ChatClient;
import org.springframework.ai.chat.client.advisor.QuestionAnswerAdvisor;
import org.springframework.ai.chat.model.ChatResponse;
import org.springframework.ai.document.Document;
import org.springframework.ai.evaluation.EvaluationRequest;
import org.springframework.ai.evaluation.EvaluationResponse;
import org.springframework.ai.evaluation.FactCheckingEvaluator;
import org.springframework.ai.evaluation.RelevancyEvaluator;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.context.annotation.Import;

import java.util.List;

import static org.assertj.core.api.AssertionsForClassTypes.assertThat;

@SpringBootTest
@Import(TestcontainersConfiguration.class)
class LLMResponseEvaluatorLiveTest {

    @Autowired
    private ChatClient contentGenerator;

    @Autowired
    private RelevancyEvaluator relevancyEvaluator;

    @Autowired
    private FactCheckingEvaluator factCheckingEvaluator;

    @Test
    void whenChatClientProvidesAnswerRelevantToTopic_thenRelevancyEvaluationSucceeds() {
        String question = "How many sick leaves can I take?";
        ChatResponse chatResponse = contentGenerator.prompt()
            .user(question)
            .call()
            .chatResponse();

        String answer = chatResponse.getResult().getOutput().getContent();
        List<Document> documents = chatResponse.getMetadata().get(QuestionAnswerAdvisor.RETRIEVED_DOCUMENTS);
        EvaluationRequest evaluationRequest = new EvaluationRequest(question, documents, answer);

        EvaluationResponse evaluationResponse = relevancyEvaluator.evaluate(evaluationRequest);
        assertThat(evaluationResponse.isPass()).isTrue();
    }

    @Test
    void whenChatClientProvidesAnswerIrrelevantToTopic_thenRelevancyEvaluationFails() {
        String question = "How many sick leaves can I take?";
        ChatResponse chatResponse = contentGenerator.prompt()
            .user(question)
            .call()
            .chatResponse();

        String nonRelevantAnswer = "A lion is the king of the jungle";
        List<Document> documents = chatResponse.getMetadata().get(QuestionAnswerAdvisor.RETRIEVED_DOCUMENTS);
        EvaluationRequest evaluationRequest = new EvaluationRequest(question, documents, nonRelevantAnswer);

        EvaluationResponse evaluationResponse = relevancyEvaluator.evaluate(evaluationRequest);
        assertThat(evaluationResponse.isPass()).isFalse();
    }

    @Test
    void whenChatClientProvidesFactuallyCorrectAnswer_thenFactCheckingEvaluationSucceeds() {
        String question = "How many sick leaves can I take?";
        ChatResponse chatResponse = contentGenerator.prompt()
            .user(question)
            .call()
            .chatResponse();

        String answer = chatResponse.getResult().getOutput().getContent();
        List<Document> documents = chatResponse.getMetadata().get(QuestionAnswerAdvisor.RETRIEVED_DOCUMENTS);
        EvaluationRequest evaluationRequest = new EvaluationRequest(question, documents, answer);

        EvaluationResponse evaluationResponse = factCheckingEvaluator.evaluate(evaluationRequest);
        assertThat(evaluationResponse.isPass()).isTrue();
    }

    @Test
    void whenChatClientProvidesFactuallyIncorrectAnswer_thenFactCheckingEvaluationFails() {
        String question = "How many sick leaves can I take?";
        ChatResponse chatResponse = contentGenerator.prompt()
            .user(question)
            .call()
            .chatResponse();

        String wrongAnswer = "You can take no leaves. Get back to work!";
        List<Document> documents = chatResponse.getMetadata().get(QuestionAnswerAdvisor.RETRIEVED_DOCUMENTS);
        EvaluationRequest evaluationRequest = new EvaluationRequest(question, documents, wrongAnswer);

        EvaluationResponse evaluationResponse = factCheckingEvaluator.evaluate(evaluationRequest);
        assertThat(evaluationResponse.isPass()).isFalse();
    }

}