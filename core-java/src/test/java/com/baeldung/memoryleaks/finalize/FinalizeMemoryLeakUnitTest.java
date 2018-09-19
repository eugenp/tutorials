package com.baeldung.memoryleaks.finalize;

import org.junit.Test;

public class FinalizeMemoryLeakUnitTest {
	@Test
	public void givenObjectWithFinalizer_whenCreatingAndDestroyingThisObject_thenMemoryLeak() {
		BulkyObject[] stock = new BulkyObject[100000];
		
		for(int i=0; i<100000; i++) {
			stock[i] = new BulkyObject();
		}
		System.out.print("Debug Point - VisuaLVM");
	}
	
	@Test
	public void givenObjectWithoutFinalizer_whenCreatingAndDestroyingThisObject_thenNoMemoryLeak() {
		BulkyObjectOptimized[] stock = new BulkyObjectOptimized[100000];
		
		for(int i=0; i<100000; i++) {
			stock[i] = new BulkyObjectOptimized();
		}
		System.out.print("Debug Point - VisuaLVM");
	}
}
