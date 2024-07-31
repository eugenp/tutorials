package com.baeldung.jpa.projection.repository;

import java.util.List;

import org.springframework.data.repository.Repository;

import com.baeldung.jpa.projection.model.Address;
import com.baeldung.jpa.projection.view.AddressView;

public interface AddressRepository extends Repository<Address, Long> {
    List<AddressView> getAddressByState(String state);
}
