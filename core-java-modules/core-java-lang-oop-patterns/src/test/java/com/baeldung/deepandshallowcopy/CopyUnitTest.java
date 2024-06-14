package com.baeldung.deepandshallowcopy;

import org.junit.jupiter.api.Test;

import java.util.ArrayList;
import java.util.List;

import static org.assertj.core.api.AssertionsForClassTypes.assertThat;

public class CopyUnitTest {
    @Test
    public void whenCreatingShallowCopy_thenObjectsShouldNotBeSame() throws CloneNotSupportedException {
        List<String> hobbies = new ArrayList<>();
        hobbies.add("Reading");
        ShallowCopy original = new ShallowCopy();
        original.setHobbies(hobbies);

        ShallowCopy shallowCopy = (ShallowCopy) original.clone();

        assertThat(shallowCopy).isNotSameAs(original);
    }

    @Test
    public void whenModifyingOriginalObject_thenShallowCopyShouldChange() throws CloneNotSupportedException {
        List<String> hobbies = new ArrayList<>();
        hobbies.add("Reading");
        ShallowCopy original = new ShallowCopy();
        original.setHobbies(hobbies);

        ShallowCopy shallowCopy = (ShallowCopy) original.clone();
        original.getHobbies().add("Swimming");

        assertThat(shallowCopy.getHobbies()).isEqualTo(original.getHobbies());
    }

    @Test
    public void whenCreatingDeepCopy_thenObjectsShouldNotBeSame() {
        List<String> hobbies = new ArrayList<>();
        hobbies.add("Reading");
        DeepCopy original = new DeepCopy();
        original.hobbies = hobbies;

        DeepCopy deepCopy = original.copy();

        assertThat(deepCopy).isNotSameAs(original);
    }

    @Test
    public void whenModifyingOriginalObject_thenDeepCopyShouldNotChange() {
        List<String> hobbies = new ArrayList<>();
        hobbies.add("Reading");
        DeepCopy original = new DeepCopy();
        original.hobbies = hobbies;

        DeepCopy deepCopy = original.copy();
        original.hobbies.add("Swimming");

        assertThat(deepCopy.hobbies).isNotEqualTo(original.hobbies);
    }
}
