package com.baeldung.api.bulkandbatch.address;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.*;

@RestController
@RequestMapping("/api/address")
public class AddressController {

    private final AddressRepository addressRepository;

    public AddressController(@Autowired AddressRepository addressRepository) {
        this.addressRepository = addressRepository;
    }

    @PostMapping
    public Address createOrUpdateAddress(@RequestBody Address address) {
        return addressRepository.createOrUpdateAddress(address);
    }
}
