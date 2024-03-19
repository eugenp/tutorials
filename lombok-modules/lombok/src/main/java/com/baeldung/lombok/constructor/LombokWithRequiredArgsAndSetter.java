package com.baeldung.lombok.constructor;

import lombok.Getter;
import lombok.Setter;
import lombok.NonNull;
import lombok.RequiredArgsConstructor;

@Getter
@RequiredArgsConstructor
public class LombokWithRequiredArgsAndSetter {
    private final int id; // Mandatory, included in constructor

    @NonNull
    private String name; // Mandatory, included in constructor

    @Setter
    private String description; // Optional, set via setter

    @Setter
    private double price; // Optional, set via setter
}