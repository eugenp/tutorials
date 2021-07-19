package com.baeldung.synthetic;

import java.lang.reflect.Constructor;
import java.lang.reflect.Field;
import java.lang.reflect.Method;

import org.junit.Assert;
import org.junit.Test;

/**
 * Unit test for {@link SyntheticFieldDemo}, {@link SyntheticMethodDemo},
 * {@link SyntheticConstructorDemo} and {@link BridgeMethodDemo} classes.
 * 
 * @author Donato Rimenti
 *
 */
public class SyntheticUnitTest {

    /**
     * Tests that the {@link SyntheticMethodDemo.NestedClass} contains two synthetic
     * methods.
     */
    @Test
    public void givenSyntheticMethod_whenIsSinthetic_thenTrue() {
        // Checks that the nested class contains exactly two synthetic methods.
        Method[] methods = SyntheticMethodDemo.NestedClass.class.getDeclaredMethods();
        Assert.assertEquals("This class should contain only two methods", 2, methods.length);

        for (Method m : methods) {
            System.out.println("Method: " + m.getName() + ", isSynthetic: " + m.isSynthetic());
            Assert.assertTrue("All the methods of this class should be synthetic", m.isSynthetic());
        }
    }

    /**
     * Tests that {@link SyntheticConstructorDemo.NestedClass} contains a synthetic
     * constructor.
     */
    @Test
    public void givenSyntheticConstructor_whenIsSinthetic_thenTrue() {
        // Checks that the nested class contains exactly a synthetic
        // constructor.
        int syntheticConstructors = 0;
        Constructor<?>[] constructors = SyntheticConstructorDemo.NestedClass.class.getDeclaredConstructors();
        Assert.assertEquals("This class should contain only two constructors", 2, constructors.length);

        for (Constructor<?> c : constructors) {
            System.out.println("Constructor: " + c.getName() + ", isSynthetic: " + c.isSynthetic());

            // Counts the synthetic constructors.
            if (c.isSynthetic()) {
                syntheticConstructors++;
            }
        }

        // Checks that there's exactly one synthetic constructor.
        Assert.assertEquals(1, syntheticConstructors);
    }

    /**
     * Tests that {@link SyntheticFieldDemo.NestedClass} contains a synthetic field.
     */
    @Test
    public void givenSyntheticField_whenIsSinthetic_thenTrue() {
        // This class should contain exactly one synthetic field.
        Field[] fields = SyntheticFieldDemo.NestedClass.class.getDeclaredFields();
        Assert.assertEquals("This class should contain only one field", 1, fields.length);

        for (Field f : fields) {
            System.out.println("Field: " + f.getName() + ", isSynthetic: " + f.isSynthetic());
            Assert.assertTrue("All the fields of this class should be synthetic", f.isSynthetic());
        }
    }

    /**
     * Tests that {@link BridgeMethodDemo} contains a synthetic bridge method.
     */
    @Test
    public void givenBridgeMethod_whenIsBridge_thenTrue() {
        // This class should contain exactly one synthetic bridge method.
        int syntheticMethods = 0;
        Method[] methods = BridgeMethodDemo.class.getDeclaredMethods();
        for (Method m : methods) {
            System.out.println(
                    "Method: " + m.getName() + ", isSynthetic: " + m.isSynthetic() + ", isBridge: " + m.isBridge());

            // Counts the synthetic methods and checks that they are also bridge
            // methods.
            if (m.isSynthetic()) {
                syntheticMethods++;
                Assert.assertTrue("The synthetic method in this class should also be a bridge method", m.isBridge());
            }
        }
        
        // Checks that there's exactly one synthetic bridge method.
        Assert.assertEquals("There should be exactly 1 synthetic bridge method in this class", 1, syntheticMethods);
    }

}