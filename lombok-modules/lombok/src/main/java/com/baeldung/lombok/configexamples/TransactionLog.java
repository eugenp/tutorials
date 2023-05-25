package com.baeldung.lombok.configexamples;

import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.experimental.Accessors;

@AllArgsConstructor
@Getter
@Accessors(prefix = {"op"})
public class TransactionLog {
    double amount;
    String description;
}
