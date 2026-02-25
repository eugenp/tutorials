package com.baeldung.spring.aotrepository.repository;

import java.util.List;

import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.Repository;
import org.springframework.transaction.annotation.Transactional;

import com.baeldung.spring.aotrepository.entity.Address;

public interface AddressRepository extends Repository<Address, Long> {

    Address save(Address address);

    List<Address> findAllById(Iterable<Long> longs);

    @Transactional(readOnly = true)
    List<Address> findAll();

    @Query(value = "SELECT * FROM ADDRESS", nativeQuery = true)
    List<Address> nativeQueryFindAllAddresses();

    @Query(value = "SELECT u FROM Address u")
    List<Address> queryFindAllAddresses();
}
