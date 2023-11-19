package com.baeldung.messaging.postgresql.domain;

import lombok.Getter;
import lombok.RequiredArgsConstructor;

@RequiredArgsConstructor
@Getter
public enum OrderType {    
    BUY('B'),
    SELL('S');
    private final char c;
}
