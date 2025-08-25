package com.baeldung.agents;

import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.condition.EnabledIfEnvironmentVariable;
import org.springframework.ai.chat.prompt.PromptTemplate;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.core.io.Resource;

import java.io.IOException;
import java.nio.charset.Charset;
import java.util.List;

import static org.assertj.core.api.AssertionsForClassTypes.assertThat;

@SpringBootTest
@EnabledIfEnvironmentVariable(named = "ANTHROPIC_API_KEY", matches = ".*")
class ParallelizationWorkflowExecutorLiveTest {

    @Autowired
    private ParallelizationWorkflowExecutor parallelizationWorkflowExecutor;

    @Value("classpath:parallelization-workflow/resume.txt")
    private Resource resume;

    @Test
    void whenPromptWithMultipleInputsGiven_thenPromptsExecutedParallelly() throws IOException {
        PromptTemplate promptTemplate = new PromptTemplate("""
            Evaluate how well this candidate's resume matches the specific requirement.
            
            RESUME:
            ---
            %s
            ---
            
            REQUIREMENT TO EVALUATE:
            {input}
            
            Provide a match score from 1-10 and a brief assessment of how well the candidate meets this specific requirement.
            """.formatted(resume.getContentAsString(Charset.defaultCharset())));

        List<String> requirements = List.of(
            "5+ years of Java development experience with Spring framework",
            "10+ years of experience with .NET and C# development",
            "Native mobile development with Swift (iOS) and Kotlin (Android)"
        );

        List<String> evaluations = parallelizationWorkflowExecutor.execute(promptTemplate, requirements);

        assertThat(evaluations.size())
            .isEqualTo(requirements.size());
        evaluations.forEach(evaluation -> {
            assertThat(evaluation)
                .isNotBlank();
        });
    }

}