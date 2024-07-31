package com.baeldung.nullconversion.service;

import com.baeldung.nullconversion.Address;
import com.baeldung.nullconversion.Delivery;
import com.baeldung.nullconversion.Person;
import com.baeldung.nullconversion.ZipCode;
import com.google.common.base.Optional;

public class OnePersonGuavaOptionalDeliveryService extends MockOnePersonDeliveryServiceBase {


    public OnePersonGuavaOptionalDeliveryService(Person person) {
        super(person);
    }

    @Override
    public Delivery calculateDeliveryForPerson(Long id) {
        return Optional.fromNullable(getPersonById(id))
          .transform(Person::getAddress)
          .transform(Address::getZipCode)
          .transform(ZipCode::getCode)
          .transform(this::calculateDeliveryForZipCode)
          .or(Delivery.defaultDelivery());
    }

}
