package io.orkes.demo.banking.controller;

import java.util.Map;

import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RestController;

import io.orkes.demo.banking.pojos.DepositDetail;
import io.orkes.demo.banking.service.FraudCheckService;
import io.orkes.demo.banking.service.WorkflowService;
import io.orkes.demo.banking.workers.FraudCheckResult;
import lombok.AllArgsConstructor;
import lombok.extern.slf4j.Slf4j;

@Slf4j
@AllArgsConstructor
@RestController
public class APIController {

    private final FraudCheckService fraudCheckService;

    private final WorkflowService workflowService;

    @PostMapping(value = "/triggerDeposit", produces = "application/json")
    public ResponseEntity<FraudCheckResult> triggerDeposit(@RequestBody DepositDetail depositDetail) {
        log.info("Checking for fraud: {}", depositDetail);
        return ResponseEntity.ok(fraudCheckService.checkForFraud(depositDetail));
    }

    // docs-marker-start-1
    @PostMapping(value = "/checkForFraud", produces = "application/json")
    public Map<String, Object> checkForFraud(@RequestBody DepositDetail depositDetail) throws Exception {
        log.info("Checking if fraud check is required for: {}", depositDetail);
        return workflowService.executeWorkflow(depositDetail);
    }

    // docs-marker-end-1

}
