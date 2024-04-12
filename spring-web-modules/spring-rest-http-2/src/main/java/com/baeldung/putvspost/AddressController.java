package com.baeldung.putvspost;

import java.util.List;
import java.util.Optional;

import org.springframework.web.bind.annotation.DeleteMapping;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.PutMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RestController;

@RestController
public class AddressController {

    private final AddressRepository repository;

    AddressController(AddressRepository repository) {
        this.repository = repository;
    }

    @GetMapping("/addresses")
    List<Address> getAllAddresses() {
        return repository.findAll();
    }

    @GetMapping("/addresses/{id}")
    Optional<Address> getAddressesById(@PathVariable Long id) {
        return repository.findById(id);
    }

    @PostMapping("/addresses")
    Address createNewAddress(@RequestBody Address newAddress) {
        return repository.save(newAddress);
    }

    @PutMapping("/addresses/{id}")
    Address replaceEmployee(@RequestBody Address newAddress, @PathVariable Long id) {

        return repository.findById(id)
            .map(address -> {
                address.setCity(newAddress.getCity());
                address.setPostalCode(newAddress.getPostalCode());
                return repository.save(address);
            })
            .orElseGet(() -> {
                return repository.save(newAddress);
            });
    }

    @DeleteMapping("/addresses/{id}")
    void deleteEmployee(@PathVariable Long id) {
        repository.deleteById(id);
    }

}
