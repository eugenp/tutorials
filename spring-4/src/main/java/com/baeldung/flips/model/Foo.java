package com.baeldung.flips.model;

import lombok.Data;
import lombok.NonNull;
import lombok.RequiredArgsConstructor;

@Data
@RequiredArgsConstructor
public class Foo {
    @NonNull private final String name;
    @NonNull private final int id;
}
