package com.baeldung.pattern.hexagonal.architecture.domain.service;

import java.util.Optional;

import org.springframework.stereotype.Service;

import com.baeldung.pattern.hexagonal.architecture.domain.model.Address;
import com.baeldung.pattern.hexagonal.architecture.domain.ports.AddressRepository;
import com.baeldung.pattern.hexagonal.architecture.domain.ports.AddressService;

import lombok.RequiredArgsConstructor;

@Service
@RequiredArgsConstructor
public class AddressServiceImpl implements AddressService {

    private final AddressRepository addressRepository;

    @Override
    public Address save(Address address) {
        return addressRepository.save(address);
    }

    @Override
    public Optional<Address> findById(Long id) {
        return addressRepository.findById(id);
    }

    @Override
    public void deleteById(Long id) {
        addressRepository.deleteById(id);
    }
}
