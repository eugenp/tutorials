package com.baeldung.java.deepcopy;

import static org.junit.jupiter.api.Assertions.*;

import org.junit.jupiter.api.Test;

class DeepCopyUnitTest {

	@Test
	 public void whenCreatingDeepCopy_thenObjectsShouldNotbeSame() throws CloneNotSupportedException {
		
            Role r1 = new Role(11, "Software Analyst", 600000);
	    Employee e1 = new Employee(001, "Hemanth", r1);
	    Employee e2 = (Employee)e1.clone();	
		
	    e2.role.grossPay = 700000;
		
	    assertNotSame(e1.role.grossPay, e2.role.grossPay);
    }
}
