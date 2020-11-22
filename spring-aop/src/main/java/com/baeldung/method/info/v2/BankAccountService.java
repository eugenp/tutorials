package com.baeldung.method.info.v2;

import org.springframework.stereotype.Component;

@Component
public class BankAccountService {

  @AccountOperation(operation = "deposit")
  public void deposit(String accountNumber, Double amount) {
    System.out.println("Depositing " + amount + " to account " + accountNumber);
  }

  @AccountOperation(operation = "withdraw")
  public void withdraw(String accountNumber, Double amount) throws WithdrawLimitException {

    if(amount > 500.0) {
      throw new WithdrawLimitException("Withdraw limit exceeded.");
    }

    System.out.println("Withdrawing " + amount + " from account " + accountNumber);

  }


}
