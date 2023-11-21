package io.orkes.demo.banking.workers;

import com.netflix.conductor.sdk.workflow.task.*;
import io.orkes.demo.banking.pojos.*;
import io.orkes.demo.banking.service.*;
import lombok.*;
import lombok.extern.slf4j.*;
import org.springframework.stereotype.*;

import java.math.*;

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
