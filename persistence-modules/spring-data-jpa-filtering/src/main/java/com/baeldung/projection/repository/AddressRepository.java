package com.baeldung.projection.repository;

import com.baeldung.projection.view.AddressView;
import com.baeldung.projection.model.Address;
import org.springframework.data.repository.Repository;

import java.util.List;

public interface AddressRepository extends Repository<Address, Long> {
    List<AddressView> getAddressByState(String state);
}
