package com.baeldung.features.records;

public record LocationWrapper<T>(T t, String description) { }
