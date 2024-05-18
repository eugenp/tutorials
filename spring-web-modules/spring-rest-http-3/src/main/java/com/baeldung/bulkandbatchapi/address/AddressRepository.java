package com.baeldung.bulkandbatchapi.address;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.stereotype.Component;

import java.util.*;

@Component
public class AddressRepository {

    private static final Logger LOGGER = LoggerFactory.getLogger(AddressRepository.class);
    private final Map<Integer, Address> addressMap = new HashMap<>();

    public List<Address> getAddresses() {
        return new ArrayList<>(addressMap.values());
    }

    public Address createAddress(Address address) {
        Address address1 = null;
        if (address.getId() == 0) {
            address1 = getAddress(addressMap.size() + 1, address.getAddressLn1(),
                    address.getCity(), address.getPin());
            addressMap.put(address.getId(), address1);

            LOGGER.info("Created Address : {}", address1);
        }
        return address1;
    }

    private static Address getAddress(int id, String addressLn1, String city, int pinCode) {
        Address address = new Address();
        address.setId(id);
        address.setAddressLn1(addressLn1);
        address.setCity(city);
        address.setPin(pinCode);

        return address;
    }
}
