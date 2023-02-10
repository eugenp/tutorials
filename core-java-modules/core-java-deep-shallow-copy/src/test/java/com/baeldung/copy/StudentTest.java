package com.baeldung.copy;

import static org.assertj.core.api.Assertions.assertThat;

import java.io.IOException;

import org.junit.jupiter.api.Test;

class StudentTest {
    @Test
    void shouldChangeTheOriginalValuesFromShallowClone() throws CloneNotSupportedException {
        Student.Bag originalBag = new Student.Bag();
        originalBag.setSize(5);
        Student original = new Student(1, 9, originalBag);

        Student shallowCloned = original.clone();
        shallowCloned.setAge(18);
        shallowCloned.getBag()
          .setSize(10);

        assertThat(original.getAge()).isEqualTo(9);
        assertThat(original.getBag()
          .getSize()).isEqualTo(10);
    }

    @Test
    void shouldNotChangeOriginalValuesFromDeepClone() throws IOException, ClassNotFoundException {
        DeepCopyExample deepCopyExample = new DeepCopyExample();
        Student.Bag originalBag = new Student.Bag();
        originalBag.setSize(5);
        Student original = new Student(1, 9, originalBag);

        Student deepCloned = deepCopyExample.deepClone(original);
        deepCloned.setAge(18);
        deepCloned.getBag()
          .setSize(10);

        assertThat(original.getAge()).isEqualTo(9);
        assertThat(original.getBag()
          .getSize()).isEqualTo(5);
    }
}
