package com.baeldung.deep_shallow_copy_2;

import org.junit.jupiter.api.Assertions;

import com.baeldung.deep_shallow_copy_2.Example;
import java.util.Date;
import java.util.List;
import org.junit.jupiter.api.Test;

class EqualityUnitTest {

    @Test
    public void givenOriginalInstance_whenCreateCopy_thenFieldsAreTheSame() {
        Example first = new Example();
        first.setValue("SomeValue");
        first.setCreationDate(new Date());
        first.setTypeList(List.of("1", "2", "3"));

        Example second = new Example();
        second.setValue(first.getValue());
        second.setCreationDate(first.getCreationDate());
        second.setTypeList(first.getTypeList());

        Assertions.assertNotSame(first, second);
        Assertions.assertSame(first.getCreationDate(), second.getCreationDate());
    }

    @Test
    public void whenUsingCloneMethod_ThenGetObjectWithSameFieldsValues() {
        Example original = new Example();
        original.setValue("Sample");
        original.setCreationDate(new Date());
        original.setTypeList(List.of("4", "7", "8"));

        try {
            Example cloned = (Example) original.clone();

            Assertions.assertNotSame(cloned, original);
            Assertions.assertSame(cloned.getCreationDate(), original.getCreationDate());
        } catch (CloneNotSupportedException ex) { }
    }

    @Test
    public void givenExampleInstance_whenCreateNewInstanceWithSameContent_thenBothAreEqualButNotTheSame() {

        Example first = new Example();
        Date date = new Date();
        first.setCreationDate(date);

        Example second = new Example();
        second.setCreationDate((Date) date.clone());

        Assertions.assertNotSame(first.getCreationDate(), second.getCreationDate());
        Assertions.assertEquals(first.getCreationDate(), second.getCreationDate());
        Assertions.assertEquals(first, second);
    }
}