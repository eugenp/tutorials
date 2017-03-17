package com.baeldung.noinjection.main;

import java.math.BigDecimal;

import com.baeldung.noinjection.consumer.AccountTransaction;
import com.baeldung.noinjection.consumer.AccountTransfer;
import com.baeldung.noinjection.consumer.AccountTransferImpl;
import com.baeldung.noinjection.service.AccountToAccountTransfer;

public class AccountTransferMain {
    public static void main(String[] args) {
        AccountTransfer actTxn = new AccountTransferImpl();

        AccountTransaction actTransfer = new AccountToAccountTransfer();

        ((AccountTransferImpl) actTxn).setTransaction(actTransfer);
        actTxn.withdraw(new BigDecimal("10.00"));

        actTxn.deposit(new BigDecimal("20.00"));
    }

}
