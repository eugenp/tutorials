package com.baeldung.method.info;

import org.assertj.core.api.Assertions;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;

import static org.junit.jupiter.api.Assertions.assertTrue;

@SpringBootTest
class BankAccountServiceIntegrationTest {

  private Account account;

  @BeforeEach
  public void setup() {
    account = new Account();
    account.setAccountNumber("12345");
    account.setBalance(2000.0);
  }

  @Autowired
  BankAccountService bankAccountService;

  @Test
  void withdraw()  {
    bankAccountService.withdraw(account, 500.0);
    assertTrue(account.getBalance() == 1500.0);
  }

  @Test
  void withdrawWhenLimitReached()  {
    Assertions.assertThatExceptionOfType(WithdrawLimitException.class).isThrownBy(() -> bankAccountService.withdraw(account, 600.0));
    assertTrue(account.getBalance() == 2000.0);
  }

  @Test
  void deposit() {
    bankAccountService.deposit(account, 500.0);
    assertTrue(account.getBalance() == 2500.0);
  }

  @Test
  void getBalance() {
    bankAccountService.getBalance();
  }
}
