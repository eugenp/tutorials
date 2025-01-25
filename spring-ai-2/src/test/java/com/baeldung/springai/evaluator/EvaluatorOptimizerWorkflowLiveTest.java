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

    @Test
    void whenQuestionAskedThroughEvaluatorOptimizerWorkflow_thenRelevantAnswerReturned() {
        String question = "What is the annual learning budget I get? I heard from my colleague that it's only 100 dollars?";
        String answer = evaluatorOptimizerWorkflow.process(question);

        assertThat(answer)
            .isNotBlank()
            .containsAnyOf("$2,000", "$2000", "2,000", "2000");
    }

}