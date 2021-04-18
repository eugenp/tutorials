package com.baeldung.pattern.hexagonal.architecture.application.adapters;

import org.springframework.web.bind.annotation.DeleteMapping;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import com.baeldung.pattern.hexagonal.architecture.application.dto.AddressDto;
import com.baeldung.pattern.hexagonal.architecture.application.exceptions.AddressNotFoundException;
import com.baeldung.pattern.hexagonal.architecture.application.factory.AddressFactory;
import com.baeldung.pattern.hexagonal.architecture.domain.ports.AddressService;

import lombok.RequiredArgsConstructor;

@RestController
@RequestMapping("/api")
@RequiredArgsConstructor
public class AddressController {

    private final AddressService addressService;

    @PostMapping("/addresses")
    public AddressDto createAddress(@RequestBody AddressDto addressDto) {
        return AddressDto.of(addressService.save(AddressFactory.create(addressDto)));
    }

    @GetMapping("/addresses/{id}")
    public AddressDto one(@PathVariable Long id) {
        return AddressDto.of(addressService.findById(id)
            .orElseThrow(() -> new AddressNotFoundException(id)));
    }

    @DeleteMapping("/addresses/{id}")
    public void deleteAddress(@PathVariable Long id) {
        addressService.deleteById(id);
    }
}
