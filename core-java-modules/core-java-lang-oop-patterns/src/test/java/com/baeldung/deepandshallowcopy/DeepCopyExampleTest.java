package com.baeldung.deepandshallowcopy;

import org.junit.jupiter.api.Test;

import java.util.ArrayList;
import java.util.List;

import static org.assertj.core.api.Assertions.assertThat;

public class DeepCopyExampleTest {
    @Test
    public void whenCreatingDeepCopy_thenObjectsShouldNotBeSame() {
        List<DeepCopyExample.Hobby> hobbies = new ArrayList<>();
        hobbies.add(new DeepCopyExample.Hobby("Reading"));
        DeepCopyExample original = new DeepCopyExample();
        original.setHobbies(hobbies);

        DeepCopyExample deepCopy = original.copy();

        assertThat(deepCopy).isNotSameAs(original);
    }

    @Test
    public void whenModifyingOriginalObject_thenDeepCopyShouldNotChange() {
        List<DeepCopyExample.Hobby> hobbies = new ArrayList<>();
        hobbies.add(new DeepCopyExample.Hobby("Reading"));
        DeepCopyExample original = new DeepCopyExample();
        original.setHobbies(hobbies);

        DeepCopyExample deepCopy = original.copy();
        original.getHobbies().add(new DeepCopyExample.Hobby("Swimming"));

        assertThat(deepCopy.getHobbies()).isNotEqualTo(original.getHobbies());
    }
}
