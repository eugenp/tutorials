package com.baeldung.tutorial.hexagonal.message;

public interface WithdrawalMessageProcessor {

        void onMessageReceived(WithdrawalRequestMessage withdrawalRequestMessage);
}
