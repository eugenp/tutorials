package io.orkes.demo.banking.workers;

import lombok.Data;

@Data
public class FraudCheckResult {

    public enum Result {
        PASS,
        FAIL;
    }

    private Result result;
    private String reason;
}
