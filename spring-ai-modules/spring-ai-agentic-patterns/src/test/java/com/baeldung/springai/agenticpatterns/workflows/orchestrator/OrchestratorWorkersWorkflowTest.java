package com.baeldung.springai.agenticpatterns.workflows.orchestrator;

import static com.baeldung.springai.agenticpatterns.aimodels.OpsClientPrompts.EXECUTE_TEST_ON_DEPLOYED_ENV_STEPS;
import static com.baeldung.springai.agenticpatterns.aimodels.OpsOrchestratorClientPrompts.REMOTE_TESTING_ORCHESTRATION_PROMPT;
import static org.assertj.core.api.Assertions.assertThat;
import static org.mockito.Mockito.mock;
import static org.mockito.Mockito.when;

import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.junit.jupiter.MockitoExtension;
import org.springframework.ai.chat.client.DefaultChatClient;

import com.baeldung.springai.agenticpatterns.aimodels.OpsClient;
import com.baeldung.springai.agenticpatterns.aimodels.OpsOrchestratorClient;

@ExtendWith(MockitoExtension.class)
class OrchestratorWorkersWorkflowTest {

    @Mock
    private OpsOrchestratorClient opsOrchestratorClient;
    @Mock
    private OpsClient opsClient;
    @InjectMocks
    private OrchestratorWorkersWorkflow orchestratorWorkersWorkflow;

    @Test
    void remoteTestingExecution_whenEnvsToTestAreDevAndIntAndAllStepsAreSuccessful_thenSuccess() {
        String prText = "https://github.com/org/repo/pull/70";
        mockOrchestratorClient(REMOTE_TESTING_ORCHESTRATION_PROMPT + prText, new String[] { prText, "dev", "int" });
        String devPrompt1 = String.format("%s\n PR: [%s] environment", EXECUTE_TEST_ON_DEPLOYED_ENV_STEPS[0], prText + " on dev");
        String devResponse1 = prText + " on dev";
        mockOpsClient(devPrompt1, devResponse1);
        String devPrompt2 = String.format("%s\n PR: [%s] environment", EXECUTE_TEST_ON_DEPLOYED_ENV_STEPS[1], devResponse1);
        String devResponse2 = "DEV\nTest executed: 10, successful: 10.";
        mockOpsClient(devPrompt2, devResponse2);
        String intPrompt1 = String.format("%s\n PR: [%s] environment", EXECUTE_TEST_ON_DEPLOYED_ENV_STEPS[0], prText + " on int");
        String intResponse1 = prText + " on int";
        mockOpsClient(intPrompt1, intResponse1);
        String intPrompt2 = String.format("%s\n PR: [%s] environment", EXECUTE_TEST_ON_DEPLOYED_ENV_STEPS[1], intResponse1);
        String intResponse2 = "INT\nTest executed: 5, successful: 5.";
        mockOpsClient(intPrompt2, intResponse2);

        String result = orchestratorWorkersWorkflow.remoteTestingExecution(prText);

        assertThat(result).isEqualTo("DEV\nTest executed: 10, successful: 10.\nINT\nTest executed: 5, successful: 5.\n");
    }

    private void mockOrchestratorClient(String prompt, String[] response) {
        DefaultChatClient.DefaultChatClientRequestSpec requestSpec = mock(DefaultChatClient.DefaultChatClientRequestSpec.class);
        DefaultChatClient.DefaultCallResponseSpec responseSpec = mock(DefaultChatClient.DefaultCallResponseSpec.class);

        when(opsOrchestratorClient.prompt(prompt)).thenReturn(requestSpec);
        when(requestSpec.call()).thenReturn(responseSpec);
        when(responseSpec.entity(String[].class)).thenReturn(response);
    }

    private void mockOpsClient(String prompt, String response) {
        DefaultChatClient.DefaultChatClientRequestSpec requestSpec = mock(DefaultChatClient.DefaultChatClientRequestSpec.class);
        DefaultChatClient.DefaultCallResponseSpec responseSpec = mock(DefaultChatClient.DefaultCallResponseSpec.class);

        when(opsClient.prompt(prompt)).thenReturn(requestSpec);
        when(requestSpec.call()).thenReturn(responseSpec);
        when(responseSpec.content()).thenReturn(response);
    }
}
