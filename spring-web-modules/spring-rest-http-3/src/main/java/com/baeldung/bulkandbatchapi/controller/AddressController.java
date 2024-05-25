package com.baeldung.bulkandbatchapi.controller;

import com.baeldung.bulkandbatchapi.request.Address;
import com.baeldung.bulkandbatchapi.service.AddressService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.validation.annotation.Validated;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@RequestMapping("/api")
@Validated
public class AddressController {

    private final AddressService addressRepository;

    public AddressController(@Autowired AddressService addressService) {
        this.addressRepository = addressService;
    }

    @GetMapping(path = "/addresses")
    public List<Address> getAllAddresses() {
        return addressRepository.getAllAddresses();
    }
}
