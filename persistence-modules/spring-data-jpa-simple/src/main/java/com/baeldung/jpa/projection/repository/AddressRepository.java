package com.baeldung.jpa.projection.repository;

import java.util.List;

import com.baeldung.jpa.projection.view.AddressDto;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.Repository;

import com.baeldung.jpa.projection.model.Address;
import com.baeldung.jpa.projection.view.AddressView;
import org.springframework.data.repository.query.Param;

public interface AddressRepository extends Repository<Address, Long> {
    List<AddressView> getAddressByState(String state);

    @Query("SELECT c.zipCode as zipCode, c.person as person FROM Address c WHERE c.state = :state")
    List<AddressView> getViewAddressByState(@Param("state") String state);

    @Query("SELECT new com.baeldung.jpa.projection.view.AddressDto(a.zipCode," +
           "new com.baeldung.jpa.projection.view.PersonDto(p.firstName, p.lastName)) " +
           "FROM Address a JOIN a.person p WHERE a.state = :state")
    List<AddressDto> findAddressByState(@Param("state") String state);

}
