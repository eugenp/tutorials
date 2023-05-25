package com.baeldung.i.polluted;

import java.util.List;

public interface Payment {
    void initiatePayments();
    Object status();
    List<Object> getPayments();

    //Loan related methods
    void intiateLoanSettlement();
    void initiateRePayment();
}
