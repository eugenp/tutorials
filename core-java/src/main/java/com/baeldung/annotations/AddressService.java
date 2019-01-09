package com.baeldung.annotations;

public class AddressService {

    @MethodDescription("Adds an address")
    public void addAddress(Address address) {
        // write business logic
    }

    @MethodDescription(value = "Updates an address", version = 2.0)
    public void updateAddress(Address address) {
        // write business logic
    }

    @MethodDescription(value = "Deletes an address")
    public void deleteAddress(Address address) {
        // write business logic
    }
}
