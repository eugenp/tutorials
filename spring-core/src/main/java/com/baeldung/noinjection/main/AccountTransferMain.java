package com.baeldung.noinjection.main;

import java.math.BigDecimal;

import com.baeldung.noinjection.consumer.IAccountTransaction;
import com.baeldung.noinjection.consumer.IAccountTransfer;
import com.baeldung.noinjection.consumer.AccountTransferImpl;
import com.baeldung.noinjection.service.AccountToAccountTransfer;

public class AccountTransferMain {
    public static void main(String[] args) {
        IAccountTransfer actTxn = new AccountTransferImpl();

        IAccountTransaction actTransfer = new AccountToAccountTransfer();

        ((AccountTransferImpl) actTxn).setTransaction(actTransfer);
        actTxn.withdraw(new BigDecimal("10.00"));

        actTxn.deposit(new BigDecimal("20.00"));
    }

}
