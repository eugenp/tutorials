package com.baeldung.deepcopy;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertNotEquals;

import org.junit.Test;

public class DeepShallowTest {

	public static void main(String[] args) throws CloneNotSupportedException {
		Diagram d1 = new Diagram("Dog", "blue", 100, 100);
		
		// Create a copy of an object using copy constructor
		Diagram d2 = new Diagram(d1);
		
		System.out.println(d1 == d2); // false is expected as d1 and d2 are pointing to two different objects
		System.out.println(d1.name); // Dog
		System.out.println(d2.name); // Dog
		
		d2.name = "Cat";
		System.out.println(d1.name); // Dog
		System.out.println(d2.name); // Cat - Because d1 and d2 are independent of each other
		
		// Now let us use clone method to create a copy
		Diagram d3 = (Diagram) d1.clone();
		System.out.println(d1 == d2); // false
		System.out.println(d1.name); // Dog
		System.out.println(d3.name); // Dog
		
		d3.name = "Duck";
		System.out.println(d1.name); // Dog
		System.out.println(d3.name); // Duck
		
		// Now lets create a Drawing with a Diagram d1
		Drawing draw1 = new Drawing(d1);
		Drawing draw2 = (Drawing)draw1.clone();
		
		System.out.println(draw1 == draw2); // false
		System.out.println(draw1.diagram.name); // Dog
		System.out.println(draw2.diagram.name); // Dog
		
		draw2.diagram.name = "Cat";
		System.out.println(draw1.diagram.name); // Cat - Because in this case the draw2 is composed of the same object that is present in draw1 
												// and hence the change to Cat is reflected on both Drawing objects, although draw1 and draw2 are indeed two different objects
												// This phenomenon is called shallow copying, where only the reference of objects are copied over to new object and 
												// no new object instance is created
		System.out.println(draw2.diagram.name); // Cat
		
		
		// Now let us implement another class by name of Collection and try to implement deep copying
		Collection col1 = new Collection(d1);
		Collection col2 = (Collection)col1.clone();
		System.out.println(col1 == col2); // false
		System.out.println(col1.diagram.name); // Cat
		System.out.println(col2.diagram.name); // Cat
		
		col2.diagram.name = "Stork";
		System.out.println(col1.diagram.name); // Cat - Now the original object d1 that was passed to col1 has not changed its name even when col2 diagram has changed its name
											   // This phenomenon is called deep copying
		System.out.println(col2.diagram.name); // Stork
		
		
		
	}
	
	@Test
	public void testCreateDiagramCopyUsingCopyConstructor() {
		Diagram d1 = new Diagram("Dog", "black", 100, 100);
		Diagram d2 = DeepShallow.createDiagramUsingCopyConstructor(d1);
		
		assertNotEquals(d1, d2);
		assertEquals(d1.name, d2.name);
		
		d2.name = "Cat";
		assertNotEquals(d1.name, d2.name);
	}
	
	@Test
	public void testCreateDrawingUsingShallowCopy() throws CloneNotSupportedException {
		
		Diagram d1 = new Diagram("Dog", "black", 100, 100);
		Drawing draw1 = new Drawing(d1);
		
		Drawing draw2 = DeepShallow.createDrawingUsingShallowCopy(draw1);
		
		assertNotEquals(draw1, draw2);
		assertEquals(draw1.diagram.name, draw2.diagram.name);
		
		draw2.diagram.name = "Cat";
		assertEquals(draw1.diagram.name, draw2.diagram.name);	
	}
	
	@Test
	public void testCreateCollectionUsingDeepCopy() throws CloneNotSupportedException {
		
		Diagram d1 = new Diagram("Dog", "black", 100, 100);
		
		Collection col1 = new Collection(d1);
		Collection col2 = DeepShallow.createCollectionUsingDeepCopy(col1);
		
		assertNotEquals(col1, col2);
		assertEquals(col1.diagram.name, col2.diagram.name);
		
		col2.diagram.name = "Stork";
		assertNotEquals(col1.diagram.name, col2.diagram.name);
	}
}


