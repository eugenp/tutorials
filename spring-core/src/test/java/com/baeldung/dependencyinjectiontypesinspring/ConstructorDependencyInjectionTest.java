package com.baeldung.dependencyinjectiontypesinspring;

import static org.junit.Assert.assertTrue;
import static org.mockito.Mockito.mock;
import static org.mockito.Mockito.when;

import org.junit.Test;
import org.springframework.boot.test.context.SpringBootTest;

@SpringBootTest
public class ConstructorDependencyInjectionTest {

	@Test
	public void givenMyDependencyIsValid_whenConstructInjectionConstructorBased_setsMyDependency() {
		final MyDependency myDependency = mock(MyDependency.class); 
		when(myDependency.getValue()).thenReturn(10); 
		
		InjectionConstructorBased myService = new InjectionConstructorBased(myDependency); 
		
		assertTrue(myService.getMyDependencyValue() == myDependency.getValue());
	}
	
	
    
}
