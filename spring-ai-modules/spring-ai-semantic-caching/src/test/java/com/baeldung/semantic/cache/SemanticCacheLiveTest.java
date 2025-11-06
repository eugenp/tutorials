package com.baeldung.semantic.cache;

import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.condition.EnabledIfEnvironmentVariable;
import org.junit.jupiter.api.condition.EnabledIfEnvironmentVariables;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;

import static org.assertj.core.api.AssertionsForClassTypes.assertThat;

@SpringBootTest
@EnabledIfEnvironmentVariables({
    @EnabledIfEnvironmentVariable(named = "OPENAI_API_KEY", matches = ".*"),
    @EnabledIfEnvironmentVariable(named = "REDIS_URL", matches = ".*")
})
class SemanticCacheLiveTest {

    @Autowired
    private SemanticCachingService semanticCachingService;

    @Test
    void whenUsingSemanticCache_thenCacheReturnsAnswerForSemanticallyRelatedQuestion() {
        String question = "How many sick leaves can I take?";
        String answer = "No leaves allowed! Get back to work!!";
        semanticCachingService.save(question, answer);

        String rephrasedQuestion = "How many days sick leave can I take?";
        assertThat(semanticCachingService.search(rephrasedQuestion))
            .isPresent()
            .hasValue(answer);

        String unrelatedQuestion = "Can I get a raise?";
        assertThat(semanticCachingService.search(unrelatedQuestion))
            .isEmpty();
    }

}