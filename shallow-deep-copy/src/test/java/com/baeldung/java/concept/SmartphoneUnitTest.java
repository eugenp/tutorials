package com.baeldung.java.concept;

import org.junit.Test;

import static org.junit.Assert.assertEquals;
import static org.junit.Assert.assertTrue;

public class SmartphoneUnitTest {

    @Test
    public void whenChangingShallowCopy_originalChanges() {
        String snapdragonOldProcessor = "Snapdragon 695";
        String snapdragonNewProcessor = "Snapdragon 700";
        String intelProcessor = "Intel i9";
        CpuSpecification snapdragonSpec = new CpuSpecification(snapdragonOldProcessor, "2GHz");

        Smartphone samsungPhone = new Smartphone("Samsung", "S10", 499, snapdragonSpec);
        Smartphone sCopy = samsungPhone.shallowCopy();

        // All the fields are equal
        assertTrue(samsungPhone != sCopy);
        assertEquals(samsungPhone.getBrand(), sCopy.getBrand());
        assertEquals(samsungPhone.getModel(), sCopy.getModel());
        assertEquals(samsungPhone.getPrice(), sCopy.getPrice(), 0.000000001);
        // Additionally, cpuSpecification field of the original object and shallow copy refer to the same object
        assertTrue(samsungPhone.getCpuSpecification() == sCopy.getCpuSpecification());
        assertEquals(sCopy.getCpuSpecification(), snapdragonSpec);

        // Changing the original object also affects the shallow copy
        samsungPhone.getCpuSpecification().setCpu(snapdragonNewProcessor);
        assertEquals(sCopy.getCpuSpecification().getCpu(), snapdragonNewProcessor);

        // Changing the shallow object also affects the original object
        sCopy.getCpuSpecification().setCpu(intelProcessor);
        assertEquals(samsungPhone.getCpuSpecification().getCpu(), intelProcessor);
    }

    @Test
    public void whenChangingDeepCopy_originalNotAffected() {
        String snapdragonOldProcessor = "Snapdragon 695";
        String snapdragonNewProcessor = "Snapdragon 700";
        String intelProcessor = "Intel i9";
        CpuSpecification snapdragonSpec = new CpuSpecification(snapdragonOldProcessor, "2GHz");

        Smartphone samsungPhone = new Smartphone("Samsung", "S10", 499, snapdragonSpec);
        Smartphone dCopy = samsungPhone.deepCopy();

        // All the fields are equal
        assertTrue(samsungPhone != dCopy);
        assertEquals(samsungPhone.getBrand(), dCopy.getBrand());
        assertEquals(samsungPhone.getModel(), dCopy.getModel());
        assertEquals(samsungPhone.getPrice(), dCopy.getPrice(), 0.000000001);
        // Original object and the deep copy have separate instances of cpuSpecification
        assertTrue(samsungPhone.getCpuSpecification() != dCopy.getCpuSpecification());
        // But cpuSpecification values are equal as objects
        assertEquals(dCopy.getCpuSpecification(), snapdragonSpec);

        samsungPhone.getCpuSpecification().setCpu(snapdragonNewProcessor);
        assertEquals(dCopy.getCpuSpecification().getCpu(), snapdragonOldProcessor);

        dCopy.getCpuSpecification().setCpu(intelProcessor);
        assertEquals(samsungPhone.getCpuSpecification().getCpu(), snapdragonNewProcessor);
    }
}

