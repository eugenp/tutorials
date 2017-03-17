package com.baeldung.injection.withoutinjection.main;

import java.math.BigDecimal;

import com.baeldung.injection.withoutinjection.consumer.AccountTransaction;
import com.baeldung.injection.withoutinjection.consumer.AccountTransfer;
import com.baeldung.injection.withoutinjection.consumer.AccountTransferImpl;
import com.baeldung.injection.withoutinjection.service.AccountToAccountTransfer;

public class AccountTransferMain {
	public static void main(String[] args) {
		AccountTransfer actTxn= new AccountTransferImpl();
		AccountTransaction actTransfer = new AccountToAccountTransfer();
		((AccountTransferImpl)actTxn).setTransaction(actTransfer);
		actTxn.withdraw(new BigDecimal("10.00"));
		actTxn.deposit(new BigDecimal("20.00"));
	}

}
