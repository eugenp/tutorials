package com.baeldung.java_8_features;

import java.util.Optional;

public class OptionalUser {

    private OptionalAddress address;

    public Optional<OptionalAddress> getAddress() {
        return Optional.of(address);
    }

    public void setAddress(OptionalAddress address) {
        this.address = address;
    }
}
