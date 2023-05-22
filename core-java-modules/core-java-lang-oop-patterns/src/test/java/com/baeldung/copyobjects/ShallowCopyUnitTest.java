package com.baeldung.copyobjects;

import static org.junit.Assert.assertEquals;

import org.junit.Test;

public class ShallowCopyUnitTest {

	@Test
	public void whenCreatingShallowCopyWithCopyConstructor_ObjectShouldBeSameAsOriginal() {
		Address address = new Address("123", "Main Street", "New York", "state");
		Employee original = new Employee("Dave", address);
		Employee shallowCopy = new Employee(original);
		
		assertEquals(original.getName(), shallowCopy.getName());
		
		assertEquals(original.getAddress().hashCode(), shallowCopy.getAddress().hashCode());		
		
		original.getAddress().setStreet("Second St");		
		
		assertEquals("Second St", original.getAddress().getStreet());

	}
	
	@Test
	public void whenCreatingShallowCopyWithCloneMethod_ObjectShouldBeSameAsOriginal() {
		
		try {
			 Person original = new Person();
	         original.setName("John");
	         
	         Address address = new Address("1234", "Main Street", "USA", "state");        
	         original.setAddress(address);         
	    
	         Person shallowCopy = (Person) original.clone();
	         
	         assertEquals(original.getName(), shallowCopy.getName());
	 		
	 		 assertEquals(original.getAddress().hashCode(), shallowCopy.getAddress().hashCode());
	 		 
	 		 original.getAddress().setStreet("Second St");
	 		
	 		 assertEquals("Second St", original.getAddress().getStreet());	 
			
		} catch (CloneNotSupportedException e) {
            e.printStackTrace();            
        }
		 
 		 
	}
}
