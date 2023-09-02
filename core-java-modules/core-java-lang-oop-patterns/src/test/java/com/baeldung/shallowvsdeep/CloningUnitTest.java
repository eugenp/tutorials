package com.baeldung.shallowvsdeep;

import org.junit.Test;

import static org.junit.Assert.assertEquals;

public class CloningUnitTest {

    @Test
    public void whenShallowCloning_thenExpectPropertiesToBeTheSame() {
        Airplane original = new Airplane("Original Plane", new Engine("MK-123"));
        Airplane copy = original.shallowClone();

        assertEquals(original.getName(), copy.getName());
        assertEquals(original.getEngine().getModel(), copy.getEngine().getModel());
    }

    @Test
    public void whenShallowCloningAndChangingCloneProperty_thenShouldNotAffectOriginal() {
        Airplane original = new Airplane("Original Plane", new Engine("MK-123"));
        Airplane copy = original.shallowClone();
        copy.setName("Copy Plane");

        assertEquals("Original Plane", original.getName());
    }

    @Test
    public void whenShallowCloningAndChangingPilotName_thenShouldAffectOriginal() {
        Airplane original = new Airplane("Original Plane", new Engine("MK-123"), new Pilot("Original Pilot"));
        Airplane copy = original.shallowClone();
        copy.getPilot().setName("Copy Pilot");

        assertEquals("Copy Pilot", original.getPilot().getName());
    }

    @Test
    public void whenDeepCloning_thenExpectNamesToBeTheSame() {
        Airplane original = new Airplane("Original Plane", new Engine("MK-123"), new Pilot("Original Pilot"));
        Airplane copy = original.deepClone();

        assertEquals(original.getName(), copy.getName());
        assertEquals(original.getEngine().getModel(), copy.getEngine().getModel());
        assertEquals(original.getPilot().getName(), copy.getPilot().getName());
    }

    @Test
    public void whenDeepCloningAndChangingCloneReferenceProperties_thenOriginalShouldBeTheSame() {
        Airplane original = new Airplane("Original Plane", new Engine("MK-123"), new Pilot("Original Pilot"));
        Airplane copy = original.deepClone();
        copy.getPilot().setName("Copy Pilot");

        assertEquals("Original Pilot", original.getPilot().getName());
    }

}
