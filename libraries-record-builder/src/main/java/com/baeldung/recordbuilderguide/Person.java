package com.baeldung.recordbuilderguide;

import io.soabase.recordbuilder.core.RecordBuilder;

@RecordBuilder
public record Person(String name, int age) implements PersonBuilder.With {}
