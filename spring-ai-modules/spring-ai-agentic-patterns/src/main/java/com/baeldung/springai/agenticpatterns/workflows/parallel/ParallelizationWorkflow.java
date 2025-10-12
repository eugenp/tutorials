package com.baeldung.springai.agenticpatterns.workflows.parallel;

import java.util.List;
import java.util.concurrent.CompletableFuture;
import java.util.concurrent.ExecutorService;
import java.util.concurrent.Executors;
import java.util.stream.Collectors;

import org.springframework.ai.chat.client.ChatClient;
import org.springframework.stereotype.Component;

import com.baeldung.springai.agenticpatterns.aimodels.OpsClient;
import com.baeldung.springai.agenticpatterns.aimodels.OpsClientPrompts;

@Component
public class ParallelizationWorkflow {

    private final OpsClient opsClient;

    public ParallelizationWorkflow(OpsClient opsClient) {
        this.opsClient = opsClient;
    }

    public List<String> opsDeployments(String containerLink, List<String> environments, int maxConcurentWorkers) {
        try (ExecutorService executor = Executors.newFixedThreadPool(maxConcurentWorkers)) {
            List<CompletableFuture<String>> futures = environments.stream()
              .map(env -> CompletableFuture.supplyAsync(() -> {
                  try {
                      String request = OpsClientPrompts.NON_PROD_DEPLOYMENT_PROMPT + "\n Image:" + containerLink + " to environment: " + env;
                      System.out.println("Request: " + request);

                      ChatClient.ChatClientRequestSpec requestSpec = opsClient.prompt(request);
                      ChatClient.CallResponseSpec responseSpec = requestSpec.call();
                      return responseSpec.content();
                  } catch (Exception e) {
                      throw new RuntimeException("Failed to deploy to env: " + env, e);
                  }
              }, executor))
              .toList();

            // Wait for all tasks to complete
            CompletableFuture<Void> allFutures = CompletableFuture.allOf(futures.toArray(CompletableFuture[]::new));
            allFutures.join();

            return futures.stream()
              .map(CompletableFuture::join)
              .collect(Collectors.toList());
        }
    }
}
