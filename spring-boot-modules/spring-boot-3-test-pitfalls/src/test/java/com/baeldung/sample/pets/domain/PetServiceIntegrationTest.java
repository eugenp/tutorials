package com.baeldung.sample.pets.domain;

import com.baeldung.sample.test.slices.PetsDomainTest;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;

import static org.assertj.core.api.Assertions.assertThat;
import static org.mockito.Mockito.when;

@PetsDomainTest
class PetServiceIntegrationTest {

    @Autowired
    PetService service;
    @Autowired // Mock
    PetServiceRepository repository;

    @Test
    void shouldAddPetWhenNotAlreadyExisting() {
        var pet = new Pet("Dog");
        when(repository.add(pet)).thenReturn(true);
        var result = service.add(pet);
        assertThat(result).isTrue();
    }

}
