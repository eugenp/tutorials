package com.baeldung.copyobjects;

import static org.junit.Assert.assertEquals;
import static org.junit.Assert.assertNotEquals;

import org.junit.Test;

public class DeepCopyUnitTest {

	@Test
	public void whenCreatingDeepCopyWithCopyConstructor_ObjectShouldNotBeSameAsOriginal() {
		Address address = new Address("123", "Main Street", "Pune", "CA");
		Student original = new Student(1, "Dave", address);
		Student deepCopy = new Student(original);
		
		assertEquals(original.getName(), deepCopy.getName());
	
		assertNotEquals(original.getAddress().hashCode(), deepCopy.getAddress().hashCode());		
		
		original.getAddress().setStreet("Second St");	
		
		assertEquals("Main Street", deepCopy.getAddress().getStreet());
	}
	
	@Test
	public void whenCreatingDeepCopyWithCloneMethod_ObjectShouldNotBeSameAsOriginal(){		
		
		try {
			 Person original = new Person();
	         original.setName("John");
	         
	         Address address = new Address("1234", "Main Street", "USA", "state");        
	         original.setAddress(address);         
	    
	         Person deepCopy = (Person) original.clone();
	         
	         deepCopy.setAddress((Address)original.getAddress().clone());
	         
	         
			assertEquals(original.getName(), deepCopy.getName());
		
			assertNotEquals(original.getAddress().hashCode(), deepCopy.getAddress().hashCode());		
			
			original.getAddress().setStreet("Second St");	
			
			assertEquals("Main Street", deepCopy.getAddress().getStreet());
			
		} catch (CloneNotSupportedException e) {
            e.printStackTrace();            
        }		 
	}
}
