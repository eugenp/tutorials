package com.baeldung.tutorial.hexagonal.message;

import com.baeldung.tutorial.hexagonal.application.BankAccountService;

class WithdrawalMessageProcessorImpl implements WithdrawalMessageProcessor {

    private BankAccountService bankAccountService;

    WithdrawalMessageProcessorImpl(BankAccountService bankAccountService) {
        this.bankAccountService = bankAccountService;
    }

    @Override
    public void onMessageReceived(WithdrawalRequestMessage withdrawalRequestMessage) {
        bankAccountService.withdraw(withdrawalRequestMessage.getBankAccountId(), withdrawalRequestMessage.getAmount());
    }
}