package com.baeldung.springai.agenticpatterns.workflows.chain;

import static com.baeldung.springai.agenticpatterns.aimodels.OpsClientPrompts.DEV_PIPELINE_STEPS;
import static org.assertj.core.api.Assertions.assertThat;
import static org.mockito.Mockito.mock;
import static org.mockito.Mockito.when;

import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.junit.jupiter.MockitoExtension;
import org.springframework.ai.chat.client.DefaultChatClient;

import com.baeldung.springai.agenticpatterns.aimodels.DummyOpsClient;

@ExtendWith(MockitoExtension.class)
class ChainWorkflowTest {

    @Mock
    private DummyOpsClient opsClient;
    @InjectMocks
    private ChainWorkflow chainWorkflow;

    @SuppressWarnings("UnnecessaryLocalVariable")
    @Test
    void opsPipeline_whenAllStepsAreSuccessful_thenSuccess() {
        String prText = "https://github.com/org/repo/pull/70";
        String prompt1 = String.format("{%s}\n {%s}", DEV_PIPELINE_STEPS[0], prText);
        String response1 = "internal/code/path";
        mockClient(prompt1, response1);
        String prompt2 = String.format("{%s}\n {%s}", DEV_PIPELINE_STEPS[1], response1);
        String response2 = response1;
        mockClient(prompt2, response2);
        String prompt3 = String.format("{%s}\n {%s}", DEV_PIPELINE_STEPS[2], response2);
        String response3 = response2 + ". Container link: [hub.docker.com/org/repo:PR-70.1]";
        mockClient(prompt3, response3);
        String prompt4 = String.format("{%s}\n {%s}", DEV_PIPELINE_STEPS[3], response3);
        String response4 = response1;
        mockClient(prompt4, response4);
        String prompt5 = String.format("{%s}\n {%s}", DEV_PIPELINE_STEPS[4], response4);
        String response5 = "success!";
        mockClient(prompt5, response5);

        String result = chainWorkflow.opsPipeline(prText);

        assertThat("success!").isEqualTo(result);
    }

    private void mockClient(String prompt, String response) {
        DefaultChatClient.DefaultChatClientRequestSpec requestSpec = mock(DefaultChatClient.DefaultChatClientRequestSpec.class);
        DefaultChatClient.DefaultCallResponseSpec responseSpec = mock(DefaultChatClient.DefaultCallResponseSpec.class);

        when(opsClient.prompt(prompt)).thenReturn(requestSpec);
        when(requestSpec.call()).thenReturn(responseSpec);
        when(responseSpec.content()).thenReturn(response);
    }
}
