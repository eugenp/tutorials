package com.baeldung.bulkandbatchapi.address;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.stereotype.Component;
import org.springframework.web.bind.annotation.GetMapping;

import java.util.*;

@Component
public class AddressService {
    private static final Logger LOGGER = LoggerFactory.getLogger(AddressService.class);
    private final Map<String, Address> addressRepoMap = new HashMap<>();

    @GetMapping(path = "/customers")
    public List<Address> getAllAddresses() {
        return new ArrayList<>(addressRepoMap.values());
    }

    public Address createAddress(Address address) {
        LOGGER.info("Creating Address : {}", address);
        Address createdAddress = null;

        String addressUniqueKey = address.getStreet().concat(address.getCity());
        if (!addressRepoMap.containsKey(addressUniqueKey)) {
            createdAddress = getAddress(addressRepoMap.size() + 1, address.getStreet(), address.getCity());
            addressRepoMap.put(addressUniqueKey, createdAddress);
            LOGGER.info("Created Address : {}", createdAddress);
        }

        return createdAddress;
    }

    private static Address getAddress(int id, String street, String city) {
        Address address = new Address();
        address.setId(id);
        address.setStreet(street);
        address.setCity(city);

        return address;
    }
}
