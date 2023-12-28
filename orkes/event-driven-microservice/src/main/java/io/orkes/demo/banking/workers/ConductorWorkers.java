package io.orkes.demo.banking.workers;

import java.math.BigDecimal;

import org.springframework.stereotype.Component;

import com.netflix.conductor.sdk.workflow.task.InputParam;
import com.netflix.conductor.sdk.workflow.task.WorkerTask;

import io.orkes.demo.banking.pojos.DepositDetail;
import io.orkes.demo.banking.service.FraudCheckService;
import lombok.AllArgsConstructor;
import lombok.extern.slf4j.Slf4j;

@AllArgsConstructor
@Component
@Slf4j
public class ConductorWorkers {

    private final FraudCheckService fraudCheckService;

    /**
     *
     * @param amount
     * @return Given the amount, the service check if the fraud check should done before executing the transaction
     */
    @WorkerTask(value = "fraud-check-required")
    public FraudCheckResult simpleWorker(@InputParam("amount") BigDecimal amount) {
        DepositDetail dd = new DepositDetail();
        dd.setAmount(amount);
        return fraudCheckService.checkForFraud(dd);
    }

}
