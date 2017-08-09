package com.baeldung.eclipsecollections;

import static org.junit.Assert.assertTrue;

import org.eclipse.collections.api.list.MutableList;
import org.eclipse.collections.impl.block.factory.Predicates;
import org.junit.Test;

public class ConvertContainerToAnotherTest {

    @SuppressWarnings({ "unchecked", "rawtypes" })
    @Test
    public void whenConvertContainerToAnother_thenCorrect() {
        MutableList<String> cars = (MutableList) ConvertContainerToAnother.convertToList();
        
        assertTrue(cars.anySatisfy(Predicates.equal("Toyota")));
        assertTrue(cars.anySatisfy(Predicates.equal("Mercedes")));
        assertTrue(cars.anySatisfy(Predicates.equal("Volkswagen")));
    }
}
