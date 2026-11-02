package com.baeldung.a2a.client.orchestrator.jobscreening;

import com.baeldung.a2a.server.backgroundchecker.BackgroundCheckerServer;
import com.baeldung.a2a.server.salaryevaluator.SalaryEvaluatorServer;
import com.baeldung.a2a.server.skillsmatcher.SkillsMatcherServer;
import org.junit.jupiter.api.AfterAll;
import org.junit.jupiter.api.BeforeAll;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.condition.EnabledIfEnvironmentVariable;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.SpringApplication;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.context.ConfigurableApplicationContext;

import static org.assertj.core.api.Assertions.assertThat;

@SpringBootTest(classes = JobScreeningOrchestrator.class)
@EnabledIfEnvironmentVariable(named = "OPENAI_API_KEY", matches = ".*")
class JobScreeningLiveTest {

    private static ConfigurableApplicationContext skillsMatcherServerContext;
    private static ConfigurableApplicationContext salaryEvaluatorServerContext;
    private static ConfigurableApplicationContext backgroundCheckerServerContext;

    @Autowired
    private AgentRegistry agentRegistry;

    @Autowired
    private JobScreeningController jobScreeningController;

    @BeforeAll
    static void startRemoteAgentServers() {
        backgroundCheckerServerContext = SpringApplication.run(BackgroundCheckerServer.class);
        salaryEvaluatorServerContext = SpringApplication.run(SalaryEvaluatorServer.class);
        skillsMatcherServerContext = SpringApplication.run(SkillsMatcherServer.class);
    }

    @AfterAll
    static void stopRemoteAgentServers() {
        backgroundCheckerServerContext.close();
        salaryEvaluatorServerContext.close();
        skillsMatcherServerContext.close();
    }

    @Test
    void whenApplicationStarts_thenAllAgentCardsFetched() {
        String agentDescriptions = agentRegistry.describeAgents();

        assertThat(agentDescriptions)
            .contains("Background Check Agent")
            .contains("Salary Evaluator Agent")
            .contains("Skills Matcher Agent");
    }

    @Test
    void whenCandidateScreened_thenScreeningVerdictReturned() {
        var screeningRequest = new JobScreeningController.ScreeningRequest(
            "John Doe",
            "john.doe@baeldung.com",
            "Backend Developer",
            "Java, Spring Boot, AWS, Kafka",
            "Java, Spring Boot, Azure, Kafka",
            110000
        );

        var screeningResponse = jobScreeningController.screenCandidate(screeningRequest);

        assertThat(screeningResponse.verdict())
            .isNotBlank()
            .containsAnyOf("75%", "3 out of 4 skills")
            .containsIgnoringCase("AWS");
    }
}