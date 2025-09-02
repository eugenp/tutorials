package com.baeldung.springai;

import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.condition.EnabledIfEnvironmentVariable;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;

import static org.assertj.core.api.AssertionsForClassTypes.assertThat;

@SpringBootTest
@EnabledIfEnvironmentVariable(named = "OPENAI_API_KEY", matches = ".*")
class PoetryServiceLiveTest {

    @Autowired
    private PoetryService poetryService;

    @Test
    void whenPoemGenerationRequested_thenCorrectResponseReturned() {
        String genre = "playful";
        String theme = "morning coffee";

        Poem poem = poetryService.generate(genre, theme);

        assertThat(poem)
            .hasNoNullFieldsOrProperties()
            .satisfies(p -> {
                String[] lines = p.content().trim().split("\\n");
                assertThat(lines)
                    .hasSize(3);
            });
    }

}