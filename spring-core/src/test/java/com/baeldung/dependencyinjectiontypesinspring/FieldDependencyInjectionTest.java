package com.baeldung.dependencyinjectiontypesinspring;

import static org.junit.Assert.assertTrue;
import static org.junit.Assert.fail;
import static org.mockito.Mockito.mock;
import static org.mockito.Mockito.when;

import org.junit.Test;
import org.springframework.boot.test.context.SpringBootTest;

@SpringBootTest
public class FieldDependencyInjectionTest {

	
	
	@Test
	public void cotr_givenNoDependency_getsNullPointerException() {
		MyDependency myDependency = mock(MyDependency.class); 
		when(myDependency.getValue()).thenReturn(10); 
		
		InjectionFieldBased myService = new InjectionFieldBased();
		
		try {
			myService.getMyDependencyValue(); //Dependency is not injected
			fail("NullPointerException is not throws");
		} catch (NullPointerException e) {
			assertTrue(e != null);
		}
	}
	
}
