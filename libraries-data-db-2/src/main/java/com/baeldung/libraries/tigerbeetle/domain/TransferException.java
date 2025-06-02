package com.baeldung.libraries.tigerbeetle.domain;

import com.tigerbeetle.CreateTransferResult;

public class TransferException extends RuntimeException {

    private final CreateTransferResult result;

    public TransferException(CreateTransferResult result) {
        this.result = result;
    }

    public CreateTransferResult getResult() {
        return this.result;
    }
}
