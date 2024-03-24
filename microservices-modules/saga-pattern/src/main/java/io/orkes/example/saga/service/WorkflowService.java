package io.orkes.example.saga.service;

import com.netflix.conductor.common.metadata.workflow.StartWorkflowRequest;
import io.orkes.conductor.client.WorkflowClient;
import io.orkes.example.saga.pojos.FoodDeliveryRequest;
import lombok.AllArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.core.env.Environment;
import org.springframework.stereotype.Service;

import java.util.HashMap;
import java.util.Map;

@Slf4j
@AllArgsConstructor
@Service
public class WorkflowService {

    private final WorkflowClient workflowClient;
    private final Environment environment;

    public Map<String, Object> startFoodDeliveryWorkflow(FoodDeliveryRequest foodDeliveryRequest) {
        StartWorkflowRequest request = new StartWorkflowRequest();
        request.setName("FoodDeliveryWorkflow");
        request.setVersion(1);
        request.setCorrelationId("api-triggered");

        String TASK_DOMAIN_PROPERTY = "conductor.worker.all.domain";
        String domain = environment.getProperty(TASK_DOMAIN_PROPERTY, String.class, "");

        if (!domain.isEmpty()) {
            Map<String, String> taskToDomain = new HashMap<>();
            taskToDomain.put("*", domain);
            request.setTaskToDomain(taskToDomain);
        }

        Map<String, Object> inputData = new HashMap<>();
        inputData.put("customerEmail", foodDeliveryRequest.getCustomerEmail());
        inputData.put("customerName", foodDeliveryRequest.getCustomerName());
        inputData.put("customerContact", foodDeliveryRequest.getCustomerContact());
        inputData.put("restaurantId", foodDeliveryRequest.getRestaurantId());
        inputData.put("foodItems", foodDeliveryRequest.getFoodItems());
        inputData.put("additionalNotes", foodDeliveryRequest.getAdditionalNotes());
        inputData.put("address", foodDeliveryRequest.getAddress());
        inputData.put("deliveryInstructions", foodDeliveryRequest.getDeliveryInstructions());
        inputData.put("paymentAmount", foodDeliveryRequest.getPaymentAmount());
        inputData.put("paymentMethod", foodDeliveryRequest.getPaymentMethod());
        request.setInput(inputData);

        String workflowId = "";
        try {
            workflowId = workflowClient.startWorkflow(request);
            log.info("Workflow id: {}", workflowId);
        } catch (Exception ex) {
            ex.printStackTrace(System.out);
            return Map.of("error", "Order creation failure", "detail", ex.toString());
        }

        return Map.of("workflowId", workflowId);
    }
}
