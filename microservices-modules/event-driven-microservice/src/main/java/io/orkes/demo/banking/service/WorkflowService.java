package io.orkes.demo.banking.service;

import java.util.HashMap;
import java.util.Map;
import java.util.UUID;
import java.util.concurrent.CompletableFuture;
import java.util.concurrent.ExecutionException;
import java.util.concurrent.TimeUnit;
import java.util.concurrent.TimeoutException;

import org.springframework.stereotype.Service;

import com.netflix.conductor.common.metadata.workflow.StartWorkflowRequest;

import io.orkes.conductor.client.WorkflowClient;
import io.orkes.conductor.common.model.WorkflowRun;
import io.orkes.demo.banking.pojos.DepositDetail;
import lombok.AllArgsConstructor;
import lombok.extern.slf4j.Slf4j;

@Slf4j
@AllArgsConstructor
@Service
public class WorkflowService {

    private final WorkflowClient workflowClient;

    /**
     * Starts the workflow execution asynchronously
     * @param depositDetail
     * @return
     */
    public Map<String, Object> startDepositWorkflow(DepositDetail depositDetail) {
        StartWorkflowRequest request = new StartWorkflowRequest();
        request.setName("microservice_orchestration");
        Map<String, Object> inputData = new HashMap<>();
        inputData.put("amount", depositDetail.getAmount());
        inputData.put("accountId", depositDetail.getAccountId());
        request.setInput(inputData);

        String workflowId = workflowClient.startWorkflow(request);
        log.info("Workflow id: {}", workflowId);
        return Map.of("workflowId", workflowId);
    }

    /**
     * Executes the workflow, waits for it to complete and returns the output of the workflow
     * @param depositDetail
     * @return
     * @throws ExecutionException
     * @throws InterruptedException
     * @throws TimeoutException
     */
    public Map<String, Object> executeWorkflow(DepositDetail depositDetail) throws ExecutionException, InterruptedException, TimeoutException {
        StartWorkflowRequest request = new StartWorkflowRequest();
        request.setName("microservice_orchestration");
        request.setVersion(1);
        Map<String, Object> inputData = new HashMap<>();
        inputData.put("amount", depositDetail.getAmount());
        inputData.put("accountId", depositDetail.getAccountId());
        request.setInput(inputData);

        CompletableFuture<WorkflowRun> workflowRun = workflowClient.executeWorkflow(request, UUID.randomUUID()
            .toString(), 10);
        log.info("Workflow id: {}", workflowRun);

        return workflowRun.get(10, TimeUnit.SECONDS)
            .getOutput();
    }

}
