package com.baeldung.mapper;

import com.baeldung.entity.Address;
import com.baeldung.entity.Customer;
import com.baeldung.entity.DeliveryAddress;
import org.mapstruct.Mapper;
import org.mapstruct.Mapping;
import org.mapstruct.MappingTarget;

@Mapper
public interface DeliveryAddressMapper {

    @Mapping(source = "customer.firstName", target = "forename")
    @Mapping(source = "customer.lastName", target = "surname")
    @Mapping(source = "address.street", target = "street")
    @Mapping(source = "address.postalcode", target = "postalcode")
    @Mapping(source = "address.county", target = "county")
    DeliveryAddress from(Customer customer, Address address);

    @Mapping(target = "surname", ignore = true)
    @Mapping(target = "forename", ignore = true)
    @Mapping(source = "address.postalcode", target = "postalcode")
    @Mapping(source = "address.county", target = "county")
    DeliveryAddress updateAddress(@MappingTarget DeliveryAddress deliveryAddress, Address address);
}
