package com.baeldung.patterns.hexagonal_quick.domain;

import lombok.AllArgsConstructor;
import lombok.Getter;

@Getter
@AllArgsConstructor
public class ReturnRecord {
    private Book book;
    private long overdueChargeInCents;
}
