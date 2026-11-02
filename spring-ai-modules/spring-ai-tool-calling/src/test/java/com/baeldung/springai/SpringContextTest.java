package com.baeldung.springai;

import static org.assertj.core.api.Assertions.assertThat;

import org.junit.jupiter.api.Test;
import org.springframework.ai.chat.client.advisor.ToolCallingAdvisor;
import org.springframework.ai.chat.client.advisor.toolsearch.ToolSearchToolCallingAdvisor;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;

@SpringBootTest(properties = "spring.ai.openai.api-key=test-key")
class SpringContextTest {

    @Autowired
    private ToolCallingAdvisor.Builder<?> toolCallingAdvisorBuilder;

    @Test
    void whenContextLoads_thenDefaultToolCallingAdvisorIsUsed() {
        assertThat(toolCallingAdvisorBuilder).isNotInstanceOf(ToolSearchToolCallingAdvisor.Builder.class);
    }
}
