package com.baeldung.shallowanddeepcopy;

import static org.assertj.core.api.Assertions.assertThat;
import static org.junit.jupiter.api.Assertions.assertNotSame;

import java.util.UUID;

import org.junit.jupiter.api.Test;

public class DeepCopyUnitTest {

    @Test
    void whenDeepCopy_ThenNoStateIsSharedWithOriginal() {
        // given
        Dog original = new Dog(UUID.randomUUID(), 5, new Collar("Max"), "Bulldog");

        // when
        Dog deepCopy = Dog.deepCopy(original);
        deepCopy.setUniqueId(UUID.randomUUID());
        deepCopy.setAge(10);
        deepCopy.getCollar()
          .setName("Winston");
        deepCopy.setBreed("German Shepherd");

        // then
        assertThat(original.getUniqueId()).isNotEqualTo(deepCopy.getUniqueId());
        assertThat(original.getAge()).isNotEqualTo(deepCopy.getAge());
        assertThat(original.getBreed()).isNotEqualTo(deepCopy.getBreed());
        assertThat(original.getCollar()
          .getName()).isNotEqualTo(deepCopy.getCollar()
          .getName());
    }

    @Test
    void whenDeepCopy_ThenIdIsNewObjectButWithSameValue() {
        // given
        Dog original = new Dog(UUID.randomUUID(), 5, new Collar("Max"), "Bulldog");

        // when
        Dog deepCopy = Dog.deepCopy(original);

        // then
        assertNotSame(deepCopy.getUniqueId(), original.getUniqueId());
        assertThat(deepCopy.getUniqueId()).isEqualTo(original.getUniqueId());
    }
}
