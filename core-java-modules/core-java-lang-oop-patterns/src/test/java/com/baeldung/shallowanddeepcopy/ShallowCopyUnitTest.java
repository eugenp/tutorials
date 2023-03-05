package com.baeldung.shallowanddeepcopy;

import java.util.UUID;

import static org.assertj.core.api.Assertions.assertThat;

import org.junit.jupiter.api.Test;

public class ShallowCopyUnitTest {

    @Test
    void whenShallowCopy_ThenStateIsSharedWithOriginal() {
        // given
        Dog original = new Dog(UUID.randomUUID(), 5, new Collar("Max"), "Bulldog");

        // when
        Dog shallowCopy = Dog.shallowCopy(original);
        shallowCopy.setUniqueId(UUID.randomUUID());
        shallowCopy.setAge(10);
        shallowCopy.getCollar()
          .setName("Winston");
        shallowCopy.setBreed("German Shepherd");

        // then
        assertThat(original.getUniqueId()).isNotEqualTo(shallowCopy.getUniqueId());
        assertThat(original.getAge()).isNotEqualTo(shallowCopy.getAge());
        assertThat(original.getBreed()).isNotEqualTo(shallowCopy.getBreed());
        assertThat(original.getCollar()
          .getName()).isEqualTo(shallowCopy.getCollar()
          .getName());
    }
}
