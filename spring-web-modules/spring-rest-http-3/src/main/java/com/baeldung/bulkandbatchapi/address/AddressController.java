package com.baeldung.bulkandbatchapi.address;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@RequestMapping("/api/address")
public class AddressController {

    private final AddressRepository addressRepository;

    public AddressController(@Autowired AddressRepository addressRepository) {
        this.addressRepository = addressRepository;
    }

    @GetMapping
    public List<Address> getAllAddresses() {
        return addressRepository.getAddresses();
    }

    @PostMapping
    public Address createAddress(@RequestBody Address address) {
        return addressRepository.createAddress(address);
    }
}
