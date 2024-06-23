package com.baeldung.deepandshallowcopy;

import org.junit.jupiter.api.Test;

import java.util.ArrayList;
import java.util.List;

import static org.assertj.core.api.AssertionsForClassTypes.assertThat;

public class ShallowCopyExampleTest {
    @Test
    public void whenCreatingShallowCopy_thenObjectsShouldNotBeSame() throws CloneNotSupportedException {
        List<String> hobbies = new ArrayList<>();
        hobbies.add("Reading");
        ShallowCopyExample original = new ShallowCopyExample();
        original.setHobbies(hobbies);

        ShallowCopyExample shallowCopy = (ShallowCopyExample) original.clone();

        assertThat(shallowCopy).isNotSameAs(original);
    }

    @Test
    public void whenModifyingOriginalObject_thenShallowCopyShouldChange() throws CloneNotSupportedException {
        List<String> hobbies = new ArrayList<>();
        hobbies.add("Reading");
        ShallowCopyExample original = new ShallowCopyExample();
        original.setHobbies(hobbies);

        ShallowCopyExample shallowCopy = (ShallowCopyExample) original.clone();
        original.getHobbies().add("Swimming");

        assertThat(shallowCopy.getHobbies()).isEqualTo(original.getHobbies());
    }
}
