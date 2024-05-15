package com.baeldung.distinct;

import static org.junit.Assert.assertTrue;

import java.util.List;

import org.eclipse.collections.impl.block.factory.HashingStrategies;
import org.eclipse.collections.impl.utility.ListIterate;
import org.junit.Before;
import org.junit.Test;

public class DistinctWithEclipseCollectionsUnitTest {
    List<Person> personList;

    @Before
    public void init() {
        personList = PersonDataGenerator.getPersonListWithFakeValues();
    }

    @Test
    public void whenFilterListByName_thenSizeShouldBe4() {
        List<Person> personListFiltered = ListIterate.distinct(personList, HashingStrategies.fromFunction(Person::getName));
        assertTrue(personListFiltered.size() == 4);
    }

    @Test
    public void whenFilterListByAge_thenSizeShouldBe2() {
        List<Person> personListFiltered = ListIterate.distinct(personList, HashingStrategies.fromIntFunction(Person::getAge));
        assertTrue(personListFiltered.size() == 2);
    }

}
