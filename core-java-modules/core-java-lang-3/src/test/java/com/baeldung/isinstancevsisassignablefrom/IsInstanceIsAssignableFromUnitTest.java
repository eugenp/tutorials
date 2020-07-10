package com.baeldung.isinstancevsisassignablefrom;

import static org.junit.Assert.assertFalse;
import static org.junit.Assert.assertTrue;

import org.junit.Test;

public class IsInstanceIsAssignableFromUnitTest {
    
    @Test
    public void whenUsingIsInstanceProperly_desiredResultsHappen() {
        Shape shape = new Triangle();
        Triangle triangle = new Triangle();
        IsoscelesTriangle isoscelesTriangle = new IsoscelesTriangle();
        Triangle isoscelesTriangle2 = new IsoscelesTriangle();
        Shape nonspecificShape = null;
        
        assertTrue(Shape.class.isInstance(shape));
        assertTrue(Shape.class.isInstance(triangle));
        assertTrue(Shape.class.isInstance(isoscelesTriangle));
        assertTrue(Shape.class.isInstance(isoscelesTriangle2));
        assertFalse(Shape.class.isInstance(nonspecificShape));
        
        assertTrue(Triangle.class.isInstance(shape));
        assertTrue(Triangle.class.isInstance(triangle));
        assertTrue(Triangle.class.isInstance(isoscelesTriangle));
        assertTrue(Triangle.class.isInstance(isoscelesTriangle2));
        
        assertFalse(IsoscelesTriangle.class.isInstance(shape));
        assertFalse(IsoscelesTriangle.class.isInstance(triangle));
        assertTrue(IsoscelesTriangle.class.isInstance(isoscelesTriangle));
        assertTrue(IsoscelesTriangle.class.isInstance(isoscelesTriangle2));        
    }
    
    @Test
    public void whenUsingIsAssignableFromProperly_desiredResultsHappen() {
        Shape shape = new Triangle();
        Triangle triangle = new Triangle();
        IsoscelesTriangle isoscelesTriangle = new IsoscelesTriangle();
        Triangle isoscelesTriangle2 = new IsoscelesTriangle();
        
        assertFalse(shape.getClass().isAssignableFrom(Shape.class));
        assertTrue(shape.getClass().isAssignableFrom(shape.getClass()));
        assertTrue(shape.getClass().isAssignableFrom(triangle.getClass()));
        assertTrue(shape.getClass().isAssignableFrom(isoscelesTriangle.getClass()));
        assertTrue(shape.getClass().isAssignableFrom(isoscelesTriangle2.getClass()));
        
        assertFalse(triangle.getClass().isAssignableFrom(Shape.class));
        assertTrue(triangle.getClass().isAssignableFrom(shape.getClass()));
        assertTrue(triangle.getClass().isAssignableFrom(triangle.getClass()));
        assertTrue(triangle.getClass().isAssignableFrom(isoscelesTriangle.getClass()));
        assertTrue(triangle.getClass().isAssignableFrom(isoscelesTriangle2.getClass()));
        
        assertFalse(isoscelesTriangle.getClass().isAssignableFrom(Shape.class));
        assertFalse(isoscelesTriangle.getClass().isAssignableFrom(shape.getClass()));
        assertFalse(isoscelesTriangle.getClass().isAssignableFrom(triangle.getClass()));
        assertTrue(isoscelesTriangle.getClass().isAssignableFrom(isoscelesTriangle.getClass()));
        assertTrue(isoscelesTriangle.getClass().isAssignableFrom(isoscelesTriangle2.getClass()));
        
        assertFalse(isoscelesTriangle2.getClass().isAssignableFrom(Shape.class));
        assertFalse(isoscelesTriangle2.getClass().isAssignableFrom(shape.getClass()));
        assertFalse(isoscelesTriangle2.getClass().isAssignableFrom(triangle.getClass()));
        assertTrue(isoscelesTriangle2.getClass().isAssignableFrom(isoscelesTriangle.getClass()));
        assertTrue(isoscelesTriangle2.getClass().isAssignableFrom(isoscelesTriangle2.getClass()));
    }

}
