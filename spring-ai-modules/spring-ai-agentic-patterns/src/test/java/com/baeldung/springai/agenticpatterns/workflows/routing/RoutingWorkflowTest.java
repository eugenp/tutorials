package com.baeldung.springai.agenticpatterns.workflows.routing;

import static org.assertj.core.api.Assertions.assertThat;
import static org.mockito.ArgumentMatchers.anyString;
import static org.mockito.Mockito.mock;
import static org.mockito.Mockito.when;

import java.util.List;

import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.junit.jupiter.MockitoExtension;
import org.springframework.ai.chat.client.DefaultChatClient;

import com.baeldung.springai.agenticpatterns.aimodels.DummyOpsRouterClient;
import com.baeldung.springai.agenticpatterns.workflows.chain.ChainWorkflow;
import com.baeldung.springai.agenticpatterns.workflows.parallel.ParallelizationWorkflow;

@ExtendWith(MockitoExtension.class)
class RoutingWorkflowTest {

    @Mock
    private DummyOpsRouterClient routerClient;
    @SuppressWarnings("unused")
    @Mock
    private ChainWorkflow chainWorkflow;
    @Mock
    private ParallelizationWorkflow parallelizationWorkflow;
    @InjectMocks
    private RoutingWorkflow routingWorkflow;

    @Test
    void opsPipeline_whenAllStepsAreSuccessful_thenSuccess() {
        String input = "please deploy hub.docker.com/org/repo:PR-70.1 to dev and test";
        String successResponse = "success!";
        mockRouterClient(new String[] { "deployment", "hub.docker.com/org/repo:PR-70.1\ndev,test\n3" });
        when(parallelizationWorkflow.opsDeployments("hub.docker.com/org/repo:PR-70.1", List.of("dev", "test"), 3)).thenReturn(
          List.of(successResponse, successResponse));

        String response = routingWorkflow.route(input);

        assertThat(response).isEqualTo("success!, success!");
    }

    private void mockRouterClient(String[] response) {
        DefaultChatClient.DefaultChatClientRequestSpec requestSpec = mock(DefaultChatClient.DefaultChatClientRequestSpec.class);
        DefaultChatClient.DefaultCallResponseSpec responseSpec = mock(DefaultChatClient.DefaultCallResponseSpec.class);

        when(routerClient.prompt(anyString())).thenReturn(requestSpec);
        when(requestSpec.call()).thenReturn(responseSpec);
        when(responseSpec.entity(String[].class)).thenReturn(response);
    }
}
