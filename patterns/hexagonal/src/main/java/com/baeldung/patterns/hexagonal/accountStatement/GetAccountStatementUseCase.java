package com.baeldung.patterns.hexagonal.accountStatement;

import com.baeldung.patterns.hexagonal.domain.Account;
import lombok.RequiredArgsConstructor;

public interface GetAccountStatementUseCase {

  Account getAccountStatement();

  @RequiredArgsConstructor
  class GetAccountStatementCommand {
    private final String accountId;

    // Constructor and getters omitted
  }

}
