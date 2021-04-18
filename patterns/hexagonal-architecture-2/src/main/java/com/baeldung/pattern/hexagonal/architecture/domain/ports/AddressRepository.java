package com.baeldung.pattern.hexagonal.architecture.domain.ports;

import com.baeldung.pattern.hexagonal.architecture.domain.model.Address;
import java.util.Optional;

public interface AddressRepository {
    
    Optional<Address> findById(Long id);

    void deleteById(Long id);

    Address save(Address address);
}
