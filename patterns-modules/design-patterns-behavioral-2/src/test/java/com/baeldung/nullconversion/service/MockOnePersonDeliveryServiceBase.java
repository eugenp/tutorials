package com.baeldung.nullconversion.service;

import com.baeldung.nullconversion.Address;
import com.baeldung.nullconversion.Delivery;
import com.baeldung.nullconversion.DeliveryService;
import com.baeldung.nullconversion.Person;
import com.baeldung.nullconversion.ZipCode;
import java.util.Optional;

public abstract class MockOnePersonDeliveryServiceBase implements DeliveryService {

    private final Person person;

    public MockOnePersonDeliveryServiceBase(Person person) {
        this.person = person;
    }

    @Override
    public Delivery calculateDeliveryForPerson(Long id) {
        Person person = getPersonById(id);
        if (person != null && person.getAddress() != null && person.getAddress().getZipCode() != null) {
            ZipCode zipCode = person.getAddress().getZipCode();
            String code = zipCode.getCode();
            return calculateDeliveryForZipCode(code);
        }
        return Delivery.defaultDelivery();
    }

    public Delivery calculateDeliveryForPersonWithOptional(Long id) {
        return Optional.ofNullable(getPersonById(id))
          .map(Person::getAddress)
          .map(Address::getZipCode)
          .map(ZipCode::getCode)
          .map(this::calculateDeliveryForZipCode)
          .orElse(Delivery.defaultDelivery());
    }

    protected Person getPersonById(Long id) {
        return person;
    }

    protected Delivery calculateDeliveryForZipCode(String zipCode) {
        if (zipCode == null || zipCode.isEmpty()) {
            return Delivery.defaultDelivery();
        } else {
            return Delivery.freeDelivery();
        }
    }

}
