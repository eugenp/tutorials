package com.baeldung.pattern.hexagonal.architecture.infrastructure.adapters;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import com.baeldung.pattern.hexagonal.architecture.domain.model.Address;
import com.baeldung.pattern.hexagonal.architecture.domain.ports.AddressRepository;

@Repository
public interface DbAddressRepository extends JpaRepository<Address, Long>, AddressRepository {

}
