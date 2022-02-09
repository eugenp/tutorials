package com.baeldung.lombok.with;

import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.With;

@Getter
@AllArgsConstructor
public abstract class Device {
    private final String serial;
    @With
    private final boolean isInspected;
}