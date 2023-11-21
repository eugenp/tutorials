package io.orkes.demo.banking.service;

import io.orkes.demo.banking.pojos.DepositDetail;
import io.orkes.demo.banking.workers.FraudCheckResult;
import org.springframework.stereotype.Service;

import java.math.BigDecimal;

import static io.orkes.demo.banking.workers.FraudCheckResult.Result.FAIL;
import static io.orkes.demo.banking.workers.FraudCheckResult.Result.PASS;

@Service
public class FraudCheckService {

    public FraudCheckResult checkForFraud(DepositDetail depositDetail) {
        FraudCheckResult fcr = new FraudCheckResult();
        if(depositDetail.getAmount().compareTo(BigDecimal.valueOf(100000)) > 0) {
            fcr.setResult(FAIL);
            fcr.setReason("Amount too large");
        } else {
            fcr.setResult(PASS);
            fcr.setReason("All good!");
        }
        return fcr;
    }

}
