package com.baeldung.springai.evaluator;

import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.context.annotation.Import;

import static org.assertj.core.api.AssertionsForClassTypes.assertThat;

@SpringBootTest
@Import(TestcontainersConfiguration.class)
class EvaluatorOptimizerWorkflowLiveTest {

    @Autowired
    private EvaluatorOptimizerWorkflow evaluatorOptimizerWorkflow;

    /**
     * Relevant document: src/main/resources/documents/employee-development.md
     */
    @Test
    void whenEmployeeDevelopmentQuestionAsked_thenRelevantAnswerReturned() {
        String question = "What is the annual learning budget I get? And what are the learning portals I get access to?";
        String answer = evaluatorOptimizerWorkflow.process(question);

        assertThat(answer)
            .isNotBlank()
            .containsAnyOf("$2,000", "$2000", "2,000", "2000")
            .containsIgnoringCase("LinkedIn Learning")
            .containsIgnoringCase("Udemy Business");
    }

    /**
     * Relevant document: src/main/resources/documents/leave-policy.md
     */
    @Test
    void whenLeavePolicyQuestionAsked_thenRelevantAnswerReturned() {
        String question = "How many sick days do I get per year? And how long in advance do I have to submit my vacation leaves?";
        String answer = evaluatorOptimizerWorkflow.process(question);

        assertThat(answer)
            .isNotBlank()
            .containsAnyOf("14 days", "14 sick days")
            .containsAnyOf("2 weeks", "two weeks");
    }

    /**
     * Relevant document: src/main/resources/documents/remote-work.md
     */
    @Test
    void whenRemoteWorkQuestionAsked_thenRelevantAnswerReturned() {
        String question = "I can begin remote work after 15 days of joining the company, right? And do we use Whatsapp for communication?";
        String answer = evaluatorOptimizerWorkflow.process(question);

        assertThat(answer)
            .isNotBlank()
            .contains("3 months", "Slack");
    }

}