package com.baeldung.nullconversion.service;

import com.baeldung.nullconversion.Address;
import com.baeldung.nullconversion.Delivery;
import com.baeldung.nullconversion.Person;
import com.baeldung.nullconversion.ZipCode;
import java.util.Optional;

public class OnePersonOptionalDeliveryService extends MockOnePersonDeliveryServiceBase {


    public OnePersonOptionalDeliveryService(Person person) {
        super(person);
    }

    @Override
    public Delivery calculateDeliveryForPerson(Long id) {
        return Optional.ofNullable(getPersonById(id))
          .map(Person::getAddress)
          .map(Address::getZipCode)
          .map(ZipCode::getCode)
          .map(this::calculateDeliveryForZipCode)
          .orElse(Delivery.defaultDelivery());
    }

}
