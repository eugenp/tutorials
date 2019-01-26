package com.baeldung.tutorial.hexagonal.message;

public interface WithdrawalRequestProcessor {

    void onRequest(WithdrawalRequest withdrawalRequest);
}
