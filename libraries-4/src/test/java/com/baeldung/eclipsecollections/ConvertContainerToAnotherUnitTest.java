package com.baeldung.eclipsecollections;

import org.assertj.core.api.Assertions;
import org.eclipse.collections.api.list.MutableList;
import org.eclipse.collections.impl.list.mutable.FastList;
import org.junit.Test;

public class ConvertContainerToAnotherUnitTest {

    @SuppressWarnings({ "unchecked", "rawtypes" })
    @Test
    public void whenConvertContainerToAnother_thenCorrect() {
        MutableList<String> cars = (MutableList) ConvertContainerToAnother.convertToList();

        Assertions.assertThat(cars).containsExactlyElementsOf(FastList.newListWith("Volkswagen", "Toyota", "Mercedes"));
    }
}
