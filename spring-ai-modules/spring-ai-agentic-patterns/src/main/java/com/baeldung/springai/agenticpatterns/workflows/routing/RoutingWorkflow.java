package com.baeldung.springai.agenticpatterns.workflows.routing;

import static com.baeldung.springai.agenticpatterns.aimodels.OpsRouterClientPrompts.OPS_ROUTING_OPTIONS;

import java.util.Arrays;
import java.util.List;
import java.util.Map;

import org.springframework.ai.chat.client.ChatClient;
import org.springframework.stereotype.Component;

import com.baeldung.springai.agenticpatterns.aimodels.OpsRouterClient;
import com.baeldung.springai.agenticpatterns.workflows.chain.ChainWorkflow;
import com.baeldung.springai.agenticpatterns.workflows.parallel.ParallelizationWorkflow;

@Component
public class RoutingWorkflow {

    private final OpsRouterClient opsRouterClient;
    private final ChainWorkflow chainWorkflow;
    private final ParallelizationWorkflow parallelizationWorkflow;

    public RoutingWorkflow(OpsRouterClient opsRouterClient, ChainWorkflow chainWorkflow, ParallelizationWorkflow parallelizationWorkflow) {
        this.opsRouterClient = opsRouterClient;
        this.chainWorkflow = chainWorkflow;
        this.parallelizationWorkflow = parallelizationWorkflow;
    }

    public String route(String input) {
        // Determine the appropriate route for the input
        String[] route = determineRoute(input, OPS_ROUTING_OPTIONS);
        String opsOperation = route[0];
        List<String> requestValues = route[1].lines()
          .toList();

        // Get the selected operation from the router and send the request
        // (the outcome is already printed out in the relevant model)
        return switch (opsOperation) {
            case "pipeline" -> chainWorkflow.opsPipeline(requestValues.getFirst());
            case "deployment" -> executeDeployment(requestValues);
            default -> throw new IllegalStateException("Unexpected value: " + opsOperation);
        };
    }

    @SuppressWarnings("SameParameterValue")
    private String[] determineRoute(String input, Map<String, String> availableRoutes) {
        String request = String.format("""
          Given this map that provides the ops operation as key and the description for you to build the operation value, as value: %s.
          Analyze the input and select the most appropriate operation.
          Return an array of two strings. First string is the operations decided and second is the value you built based on the operation.
          
          Input: %s""", availableRoutes, input);

        ChatClient.ChatClientRequestSpec requestSpec = opsRouterClient.prompt(request);
        ChatClient.CallResponseSpec responseSpec = requestSpec.call();
        String[] routingResponse = responseSpec.entity(String[].class);
        System.out.printf("Routing Decision: Operation is: %s\n, Operation value: %s%n", routingResponse[0], routingResponse[1]);

        return routingResponse;
    }

    private String executeDeployment(List<String> requestValues) {
        String containerLink = requestValues.getFirst();
        List<String> environments = Arrays.asList(requestValues.get(1)
          .split(","));
        int maxWorkers = Integer.parseInt(requestValues.getLast());

        List<String> results = parallelizationWorkflow.opsDeployments(containerLink, environments, maxWorkers);

        return String.join(", ", results);
    }
}
