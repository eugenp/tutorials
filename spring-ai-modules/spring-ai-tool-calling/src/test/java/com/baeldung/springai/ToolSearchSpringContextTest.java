package com.baeldung.springai;

import static org.assertj.core.api.Assertions.assertThat;

import org.junit.jupiter.api.Test;
import org.springframework.ai.chat.client.advisor.ToolCallingAdvisor;
import org.springframework.ai.chat.client.advisor.toolsearch.ToolSearchToolCallingAdvisor;
import org.springframework.ai.tool.toolsearch.ToolIndex;
import org.springframework.ai.tool.toolsearch.index.lucene.LuceneToolIndex;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.test.context.ActiveProfiles;

@ActiveProfiles("tool-search")
@SpringBootTest(properties = "spring.ai.openai.api-key=test-key")
class ToolSearchSpringContextTest {

    @Autowired
    private ToolCallingAdvisor.Builder<?> toolCallingAdvisorBuilder;

    @Autowired
    private ToolIndex toolIndex;

    @Test
    void givenToolSearchProfile_whenContextLoads_thenToolSearchAdvisorReplacesDefaultOne() {
        assertThat(toolCallingAdvisorBuilder).isInstanceOf(ToolSearchToolCallingAdvisor.Builder.class);
        assertThat(toolIndex).isInstanceOf(LuceneToolIndex.class);
    }
}
