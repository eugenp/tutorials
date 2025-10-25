/*
 * Copyright 2025 The Dapr Authors
 * Licensed under the Apache License, Version 2.0 (the "License");
 * you may not use this file except in compliance with the License.
 * You may obtain a copy of the License at
 *     http://www.apache.org/licenses/LICENSE-2.0
 * Unless required by applicable law or agreed to in writing, software
 * distributed under the License is distributed on an "AS IS" BASIS,
 * WITHOUT WARRANTIES OR CONDITIONS OF ANY KIND, either express or implied.
 * See the License for the specific language governing permissions and
limitations under the License.
*/

package io.dapr.springboot.workflows.simplehttp;

import io.dapr.springboot.workflows.model.PaymentRequest;
import io.dapr.workflows.Workflow;
import io.dapr.workflows.WorkflowStub;
import io.dapr.workflows.WorkflowTaskOptions;
import io.dapr.workflows.WorkflowTaskRetryPolicy;
import org.springframework.stereotype.Component;

import java.time.Duration;

@Component
public class MakeHttpPaymentWorkflow implements Workflow {

  @Override
  public WorkflowStub create() {
    return ctx -> {
      String instanceId = ctx.getInstanceId();
      ctx.getLogger().info("Workflow instance " + instanceId + " started");
      PaymentRequest paymentRequest = ctx.getInput(PaymentRequest.class);

      WorkflowTaskRetryPolicy workflowTaskRetryPolicy = new WorkflowTaskRetryPolicy(2,
              Duration.ofSeconds(1),
              1.0,
              Duration.ofSeconds(5),
              Duration.ofSeconds(10));
      ctx.getLogger().info("Let's make a payment: " + paymentRequest.getId()
              + " for customer: " + paymentRequest.getCustomer());
      paymentRequest = ctx.callActivity(MakePaymentRequestActivity.class.getName(), paymentRequest,
              new WorkflowTaskOptions(workflowTaskRetryPolicy),
              PaymentRequest.class).await();

      ctx.getLogger().info("Payment request: " + paymentRequest
              + " sent to payment service.");

      ctx.complete(paymentRequest);
    };
  }
}


