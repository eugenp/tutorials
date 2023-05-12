package com.baeldung.sample.pets.domain;

import org.junit.jupiter.api.Test;

import static org.assertj.core.api.Assertions.assertThat;

class PetServiceUnitTest {

    PetService service = new PetService(new PetServiceRepositoryImpl());

    @Test
    void shouldAddPetWhenNotAlreadyExisting() {
        var pet = new Pet("Dog");
        var result = service.add(pet);
        assertThat(result).isTrue();
        assertThat(service.getPets()).hasSize(1);
    }

    @Test
    void shouldNotAddPetWhenAlreadyExisting() {
        var pet = new Pet("Cat");
        var result = service.add(pet);
        assertThat(result).isTrue();
        // try a second time
        result = service.add(pet);
        assertThat(result).isFalse();
        assertThat(service.getPets()).hasSize(1);
    }


}
