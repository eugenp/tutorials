package com.example.quick.hexagonal.module;

import com.example.quick.hexagonal.app.BankingConsoleServiceImpl;
import com.example.quick.hexagonal.database.BankAccountRepository;
import com.example.quick.hexagonal.database.InMemoryBankRepository;
import com.example.quick.hexagonal.domain.BankService;
import com.example.quick.hexagonal.domain.BankServiceImpl;
import com.example.quick.hexagonal.services.Accounts;
import com.example.quick.hexagonal.services.AccountsService;
import com.example.quick.hexagonal.services.Transfers;
import com.example.quick.hexagonal.services.TransfersService;
import com.example.quick.hexagonal.log.EventLog;
import com.example.quick.hexagonal.log.StdOutErrorLog;
import com.google.inject.AbstractModule;

public class BankModule extends AbstractModule {
  @Override
  protected void configure() {
    bind(BankAccountRepository.class).to(InMemoryBankRepository.class);
    bind(EventLog.class).to(StdOutErrorLog.class);
    bind(Accounts.class).to(AccountsService.class);
    bind(Transfers.class).to(TransfersService.class);
    bind(BankService.class).to(BankServiceImpl.class);
  }
}
