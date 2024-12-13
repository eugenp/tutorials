package com.baeldung.jpa.projection.repository;

import java.util.List;

import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.Repository;

import com.baeldung.jpa.projection.model.Address;
import com.baeldung.jpa.projection.view.AddressView;
import org.springframework.data.repository.query.Param;

public interface AddressRepository extends Repository<Address, Long> {
    List<AddressView> getAddressByState(String state);

    @Query("SELECT c.zipCode as zipCode, c.person as person FROM Address c WHERE c.state = :state")
    List<AddressView> getViewAddressByState(@Param("state") String state);
}
