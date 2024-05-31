package com.baeldung.bulkandbatchapi.service;

import com.baeldung.bulkandbatchapi.request.Address;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.stereotype.Service;

import java.util.*;

@Service
public class AddressService {
    private static final Logger LOGGER = LoggerFactory.getLogger(AddressService.class);
    private final Map<String, Address> addressRepoMap = new HashMap<>();

    public Address createAddress(Address address) {
        LOGGER.info("Creating Address : {}", address);
        Address createdAddress = null;

        String addressUniqueKey = address.getStreet().concat(address.getCity());
        if (!addressRepoMap.containsKey(addressUniqueKey)) {
            createdAddress = new Address(addressRepoMap.size() + 1, address.getStreet(), address.getCity());
            addressRepoMap.put(addressUniqueKey, createdAddress);
            LOGGER.info("Created Address : {}", createdAddress);
        }

        return createdAddress;
    }
}
