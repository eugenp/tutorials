package com.baeldung.apache.avro.model;

import org.apache.avro.reflect.Union;

public record BankAccountWithAbstractField(String bankAccountNumber,
                                           @Union({ PersonalBankAccountReference.class, BusinessBankAccountReference.class }) AccountReference reference) {

    interface AccountReference {

        String reference();
    }

    record PersonalBankAccountReference(String reference, String holderName) implements AccountReference {

    }

    record BusinessBankAccountReference(String reference, String businessEntityId) implements AccountReference {

    }

}