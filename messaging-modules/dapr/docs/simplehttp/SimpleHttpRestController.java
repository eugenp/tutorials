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

import io.dapr.spring.workflows.config.EnableDaprWorkflows;
import io.dapr.springboot.workflows.model.PaymentRequest;
import io.dapr.springboot.workflows.service.PaymentRequestsStore;
import io.dapr.springboot.workflows.service.PaymentWorkflowsStore;
import io.dapr.workflows.client.DaprWorkflowClient;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RestController;

@RestController
@EnableDaprWorkflows
public class SimpleHttpRestController {

  private final Logger logger = LoggerFactory.getLogger(SimpleHttpRestController.class);

  @Autowired
  private DaprWorkflowClient daprWorkflowClient;

  @Autowired
  private PaymentWorkflowsStore paymentsWorkflowsStore;


  //@TODO: as this is an example, we store incoming requests in-memory
  @Autowired
  private PaymentRequestsStore paymentRequestsStore;

  /**
   * Simple Payment workflow
   *
   * @param paymentRequest to be sent to a remote http service
   * @return workflow instance id created for the payment
   */
  @PostMapping("/simplehttp/start")
  public PaymentRequest placePaymentRequest(@RequestBody PaymentRequest paymentRequest) {
    String instanceId = daprWorkflowClient.scheduleNewWorkflow(MakeHttpPaymentWorkflow.class, paymentRequest);
    paymentRequest.setWorkflowInstanceId(instanceId);
    paymentsWorkflowsStore.savePaymentWorkflow(paymentRequest, instanceId);
    return paymentRequest;
  }

}

