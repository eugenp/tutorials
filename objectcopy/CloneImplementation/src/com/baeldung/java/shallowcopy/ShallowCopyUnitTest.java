package com.baeldung.java.shallowcopy;

import static org.junit.jupiter.api.Assertions.*;

import org.junit.jupiter.api.Test;

class ShallowCopyUnitTest {

	@Test
	public void whenCreatingShallowCopy_thenObjectsShouldbeSame() throws CloneNotSupportedException {
		
          Role r1 = new Role(11, "Software Analyst", 600000);
	  Employee e1 = new Employee(001, "Hemanth", r1);
	  Employee e2 = (Employee)e1.clone();	
		
	  e2.role.level = 10;
		
	  assertEquals(e1.role.level,e2.role.level);
    }
}
