package com.baeldung.shallowanddeepcopy;

import static org.assertj.core.api.Assertions.assertThat;

import java.util.UUID;

import org.junit.jupiter.api.Test;

public class CopyUnitTest {

    @Test
    void whenCopy_ThenNoStateIsSharedWithOriginalAndNewIdIsGenerated() {
        // given
        Dog original = new Dog(UUID.randomUUID(), 5, new Collar("Max"), "Bulldog");

        // when
        Dog copy = Dog.copy(original);
        copy.setAge(10);
        copy.getCollar()
          .setName("Winston");
        copy.setBreed("German Shepherd");

        // then
        assertThat(original.getUniqueId()).isNotEqualTo(copy.getUniqueId());
        assertThat(original.getAge()).isNotEqualTo(copy.getAge());
        assertThat(original.getBreed()).isNotEqualTo(copy.getBreed());
        assertThat(original.getCollar()
          .getName()).isNotEqualTo(copy.getCollar()
          .getName());
    }
}
