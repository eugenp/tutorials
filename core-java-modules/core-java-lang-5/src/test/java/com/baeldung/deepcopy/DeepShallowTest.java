package com.baeldung.deepcopy;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertNotEquals;

import org.junit.Test;

public class DeepShallowTest {
	
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


