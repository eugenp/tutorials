package com.baeldung.springai.agenticpatterns.workflows.orchestrator;

import static com.baeldung.springai.agenticpatterns.aimodels.OpsOrchestratorClientPrompts.REMOTE_TESTING_ORCHESTRATION_PROMPT;

import org.springframework.ai.chat.client.ChatClient;
import org.springframework.stereotype.Component;

import com.baeldung.springai.agenticpatterns.aimodels.OpsClient;
import com.baeldung.springai.agenticpatterns.aimodels.OpsClientPrompts;
import com.baeldung.springai.agenticpatterns.aimodels.OpsOrchestratorClient;

@Component
public class OrchestratorWorkersWorkflow {

    private final OpsOrchestratorClient opsOrchestratorClient;
    private final OpsClient opsClient;

    public OrchestratorWorkersWorkflow(OpsOrchestratorClient opsOrchestratorClient, OpsClient opsClient) {
        this.opsOrchestratorClient = opsOrchestratorClient;
        this.opsClient = opsClient;
    }

    public String remoteTestingExecution(String userInput) {
        System.out.printf("User input: [%s]\n", userInput);
        String orchestratorRequest = REMOTE_TESTING_ORCHESTRATION_PROMPT + userInput;
        System.out.println("The prompt to orchestrator: " + orchestratorRequest);

        ChatClient.ChatClientRequestSpec orchestratorRequestSpec = opsOrchestratorClient.prompt(orchestratorRequest);
        ChatClient.CallResponseSpec orchestratorResponseSpec = orchestratorRequestSpec.call();
        String[] orchestratorResponse = orchestratorResponseSpec.entity(String[].class);
        String prLink = orchestratorResponse[0];
        StringBuilder response = new StringBuilder();

        // for each environment that we need to test on
        for (int i = 1; i < orchestratorResponse.length; i++) {
            // execute the chain steps for 1) deployment and 2) test execution
            String testExecutionChainInput = prLink + " on " + orchestratorResponse[i];
            for (String prompt : OpsClientPrompts.EXECUTE_TEST_ON_DEPLOYED_ENV_STEPS) {
                // Compose the request for the next step
                String testExecutionChainRequest =
                  String.format("%s\n PR: [%s] environment", prompt, testExecutionChainInput);
                System.out.printf("PROMPT: %s:\n", testExecutionChainRequest);

                // Call the ops client with the new request and set the result as the next step input.
                ChatClient.ChatClientRequestSpec requestSpec = opsClient.prompt(testExecutionChainRequest);
                ChatClient.CallResponseSpec responseSpec = requestSpec.call();
                testExecutionChainInput = responseSpec.content();
                System.out.printf("OUTCOME: %s\n", testExecutionChainInput);
            }
            response.append(testExecutionChainInput).append("\n");
        }

        return response.toString();
    }
}
