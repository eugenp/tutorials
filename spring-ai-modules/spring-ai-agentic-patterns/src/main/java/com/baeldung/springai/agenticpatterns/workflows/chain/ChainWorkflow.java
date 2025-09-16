package com.baeldung.springai.agenticpatterns.workflows.chain;

import org.springframework.ai.chat.client.ChatClient;
import org.springframework.stereotype.Component;

import com.baeldung.springai.agenticpatterns.aimodels.OpsClient;

@Component
public class ChainWorkflow {

    private final OpsClient opsClient;

    public ChainWorkflow(OpsClient opsClient) {
        this.opsClient = opsClient;
    }

    public String opsPipeline(String userInput) {
        String response = userInput;
        System.out.printf("User input PR link: [%s]\n", response);

        for (String prompt : ChainWorkflowUtils.DEV_PIPELINE_STEPS) {
            // Compose the request using the response from the previous step.
            String request = String.format("{%s}\n {%s}", prompt, response);
            System.out.printf("PROMPT: %s:\n", request);

            // Call the ops client with the new request and get the new response.
            ChatClient.ChatClientRequestSpec requestSpec = opsClient.prompt(request);
            ChatClient.CallResponseSpec responseSpec = requestSpec.call();
            response = responseSpec.content();
            System.out.printf("OUTCOME: %s:\n", response);

            // If there is an error, print the error and break
            if (response.startsWith("ERROR:")) {
                break;
            }
        }

        return response;
    }
}
