package com.baeldung.method.info.v2;

import org.assertj.core.api.Assertions;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;

@SpringBootTest
class BankAccountServiceIntegrationTest {

  @Autowired
  BankAccountService bankAccountService;

  @Test
  void withdraw()  {
    bankAccountService.withdraw("12345", 500.0);
  }

  @Test
  void withdrawWhenLimitReached()  {
    Assertions.assertThatExceptionOfType(WithdrawLimitException.class).isThrownBy(() -> bankAccountService.withdraw("12345", 600.0));
  }

  @Test
  void deposit() {
    bankAccountService.deposit("12345", 500.0);
  }
}
