package com.baeldung.springai.agenticpatterns.workflows.parallel;

import static org.assertj.core.api.Assertions.assertThat;
import static org.mockito.Mockito.mock;
import static org.mockito.Mockito.when;

import java.util.List;

import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.junit.jupiter.MockitoExtension;
import org.springframework.ai.chat.client.DefaultChatClient;

import com.baeldung.springai.agenticpatterns.aimodels.DummyOpsClient;
import com.baeldung.springai.agenticpatterns.aimodels.OpsClientPrompts;

@ExtendWith(MockitoExtension.class)
class ParallelizationWorkflowTest {

    @Mock
    private DummyOpsClient opsClient;
    @InjectMocks
    private ParallelizationWorkflow parallelizationWorkflow;

    @Test
    void opsPipeline_whenAllStepsAreSuccessful_thenSuccess() {
        String containerLink = "hub.docker.com/org/repo:PR-70.1";
        String successResponse = "success!";
        String prompt1 = OpsClientPrompts.NON_PROD_DEPLOYMENT_PROMPT + "\n Image:" + containerLink + " to environment: dev";
        mockClient(prompt1, successResponse);
        String prompt2 = OpsClientPrompts.NON_PROD_DEPLOYMENT_PROMPT + "\n Image:" + containerLink + " to environment: test";
        mockClient(prompt2, successResponse);
        String prompt3 = OpsClientPrompts.NON_PROD_DEPLOYMENT_PROMPT + "\n Image:" + containerLink + " to environment: demo";
        mockClient(prompt3, successResponse);

        List<String> results = parallelizationWorkflow.opsDeployments(containerLink, List.of("dev", "test", "demo"), 2);

        assertThat(results).hasSize(3);
        assertThat(results).containsExactly("success!", "success!", "success!");
    }

    private void mockClient(String prompt, String response) {
        DefaultChatClient.DefaultChatClientRequestSpec requestSpec = mock(DefaultChatClient.DefaultChatClientRequestSpec.class);
        DefaultChatClient.DefaultCallResponseSpec responseSpec = mock(DefaultChatClient.DefaultCallResponseSpec.class);

        when(opsClient.prompt(prompt)).thenReturn(requestSpec);
        when(requestSpec.call()).thenReturn(responseSpec);
        when(responseSpec.content()).thenReturn(response);
    }
}
