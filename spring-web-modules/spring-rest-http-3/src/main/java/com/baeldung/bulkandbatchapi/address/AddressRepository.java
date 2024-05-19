package com.baeldung.bulkandbatchapi.address;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.stereotype.Component;
import org.springframework.web.bind.annotation.GetMapping;

import java.util.*;

@Component
public class AddressRepository {
    private static final Logger LOGGER = LoggerFactory.getLogger(AddressRepository.class);
    private final Map<String, Address> addressMap = new HashMap<>();

    @GetMapping(path = "/customers")
    public List<Address> getAllAddresses() {
        return new ArrayList<>(addressMap.values());
    }

    public Address createAddress(Address address) {
        Address createdAddress = null;

        if (!addressMap.containsKey(address.getAddressLn1() + address.getCity())) {
            createdAddress = getAddress(addressMap.size() + 1,
                    address.getAddressLn1(),
                    address.getCity());
            addressMap.put(address.getAddressLn1() + address.getCity(), createdAddress);
            LOGGER.info("Created Address : {}", createdAddress);
        }

        return createdAddress;
    }

    private static Address getAddress(int id, String addressLn1, String city) {
        Address address = new Address();
        address.setId(id);
        address.setAddressLn1(addressLn1);
        address.setCity(city);

        return address;
    }
}
