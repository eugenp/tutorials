package shallowVsDeepCopy;

import org.junit.Test;
import static org.junit.Assert.*;

public class ShallowVsDeepTest {

	 @Test
	    public void testShallowCopy() {
	        // Initialize the first object and add elements
	        ShallowClass firstShallowObj = new ShallowClass();
	        firstShallowObj.list.add(20);
	        firstShallowObj.list.add("Hello");
	        firstShallowObj.list.add(10.5f);

	        // Create a shallow copy of the first object
	        ShallowClass secShallowObj = firstShallowObj.shallowCopy();

	        // Modify the copied object's list
	        secShallowObj.list.set(1, "World");

	        // Verify that the new list is created but the references are shared
	        assertEquals("World", secShallowObj.list.get(1)); // The change should be reflected here
	        assertEquals("World", firstShallowObj.list.get(1)); // The change should be reflected here as well

	        // Verify that the new list is indeed a different instance
	        assertNotSame(firstShallowObj.list, secShallowObj.list); // Should be different instances
	 }
    @Test
    public void testDeepCopy() {
        // Initialize the first object and add mutable and immutable elements
        DeepClass firstDeepObj = new DeepClass();
        firstDeepObj.getList().add(new MutableProperty("Initial Value")); // Mutable property
        firstDeepObj.getList().add(20); // Immutable property
        firstDeepObj.getList().add("Hello"); // Immutable property
        firstDeepObj.getList().add(10.5f); // Immutable property

        // Create a deep copy of the first object
        DeepClass secDeepObj = new DeepClass(firstDeepObj);

        // Modify the copied object's list
        ((MutableProperty) secDeepObj.getList().get(0)).setValue("World"); // Change mutable property
        secDeepObj.getList().set(1, "Updated Hello"); // Change immutable property (String)

        // Verify that the deep copy preserves the original values for mutable properties
        assertEquals("Initial Value", ((MutableProperty) firstDeepObj.getList().get(0)).getValue()); // Should be unchanged
        assertEquals("World", ((MutableProperty) secDeepObj.getList().get(0)).getValue()); // Should be updated
        assertEquals(20, firstDeepObj.getList().get(1)); // Should be unchanged
        assertEquals("Updated Hello", secDeepObj.getList().get(1)); // Should be updated
    }
}
