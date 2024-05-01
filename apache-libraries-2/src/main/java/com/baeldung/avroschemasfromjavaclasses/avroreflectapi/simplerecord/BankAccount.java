package com.baeldung.avroschemasfromjavaclasses.avroreflectapi.simplerecord;

import java.math.BigDecimal;
import java.util.List;
import java.util.UUID;

public class BankAccount {
    private String number;
    private BankAccountHolder holder;
    private BigDecimal balance;
    private List<UUID> transactionIds;
}
