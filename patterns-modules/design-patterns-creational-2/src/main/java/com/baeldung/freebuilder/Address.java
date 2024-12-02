package com.baeldung.freebuilder;

import java.util.Optional;

import org.inferred.freebuilder.FreeBuilder;

@FreeBuilder
public interface Address {

    Optional<String> getAddressLine1();

    Optional<String> getAddressLine2();

    Optional<String> getAddressLine3();

    String getCity();

    Optional<String> getState();

    Optional<Long> getPinCode();

    class Builder extends Address_Builder {

    }
}