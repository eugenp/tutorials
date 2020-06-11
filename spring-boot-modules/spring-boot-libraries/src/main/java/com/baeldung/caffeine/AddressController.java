package com.baeldung.caffeine;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RestController;

@RestController
public class AddressController
{
    @Autowired
    private AddressService addressService;

    @GetMapping("/address/{id}")
    public ResponseEntity<String> getAddress(@PathVariable("id") long customerId)
    {
        return ResponseEntity.ok(addressService.getAddress(customerId));
    }

    @GetMapping("/address2/{id}")
    public ResponseEntity<String> getAddress2(@PathVariable("id") long customerId)
    {
        return ResponseEntity.ok(addressService.getAddress2(customerId));
    }
}
