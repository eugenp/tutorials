package com.baeldung.tutorial.hexagonal.message;

import com.baeldung.tutorial.hexagonal.application.BankAccountService;

class MessageProcessorImpl implements WithdrawalRequestProcessor {

    private BankAccountService bankAccountService;

    MessageProcessorImpl(BankAccountService bankAccountService) {
        this.bankAccountService = bankAccountService;
    }

    @Override
    public void onRequest(WithdrawalRequest withdrawalRequest) {
        bankAccountService.withdraw(withdrawalRequest.getBankAccountId(), withdrawalRequest.getAmount());
    }
}