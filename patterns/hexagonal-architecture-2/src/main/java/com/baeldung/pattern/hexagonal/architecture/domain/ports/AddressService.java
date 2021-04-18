package com.baeldung.pattern.hexagonal.architecture.domain.ports;

import java.util.Optional;

import com.baeldung.pattern.hexagonal.architecture.domain.model.Address;

public interface AddressService {

    Address save(Address newAddress);

    Optional<Address> findById(Long id);

    void deleteById(Long id);

}
