package com.baeldung.lombok.equalsandhashcode.inheritance;

import org.junit.Test;

import static org.junit.Assert.assertEquals;
import static org.junit.Assert.assertNotEquals;

public class EqualsAndHashCodeUnitTest {

    @Test
    public void whenUsingEqualsAndHashCodeAndCallSuperIsTrue_thenTwoObjectsAreEqualAndHaveSameHashCodeIfAllFieldsIncludingParentsFieldsAreSame() {
        Manager manager = new Manager("Opto", 444, "Nimi", 15, 22);
        Manager managerTwo = new Manager("Opto", 444, "Nimi", 15, 22);
        Manager managerThree = new Manager("Opto", 444, "Nimi", 15, 23);

        assertEquals(manager, managerTwo);
        assertNotEquals(managerTwo, managerThree);

    }

    @Test
    public void whenUsingEqualsAndHashCodeAndCallSuperIsFalse_thenTwoObjectsAreEqualAndHaveSameHashCodeEvenIfAllParentsFieldsAreNotSame() {
        ManagerV2 manager = new ManagerV2("Opto", 444, "Nimi", 15, 22);
        ManagerV2 managerTwo = new ManagerV2("Opto", 444, "Nimi", 15, 22);
        ManagerV2 managerThree = new ManagerV2("Opto", 444, "Nimi", 14, 21);
        assertEquals(manager, managerTwo);
        assertEquals(managerTwo, managerThree);
    }
}
