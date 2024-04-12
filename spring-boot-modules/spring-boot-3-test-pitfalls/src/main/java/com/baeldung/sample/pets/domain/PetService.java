package com.baeldung.sample.pets.domain;

import lombok.RequiredArgsConstructor;
import lombok.experimental.Delegate;
import org.springframework.stereotype.Service;

@Service
@RequiredArgsConstructor
public class PetService {

    @Delegate
    private final PetServiceRepository repo;

}
