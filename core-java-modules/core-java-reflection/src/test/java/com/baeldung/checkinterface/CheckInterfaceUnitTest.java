package com.baeldung.checkinterface;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertFalse;
import static org.junit.jupiter.api.Assertions.assertTrue;

import java.util.Arrays;
import java.util.HashSet;
import java.util.List;
import java.util.Set;

import org.apache.commons.lang3.ClassUtils;
import org.junit.jupiter.api.BeforeAll;
import org.junit.jupiter.api.Test;
import org.reflections.ReflectionUtils;
import org.reflections.Reflections;

public class CheckInterfaceUnitTest {
    
    protected static Reflections reflections;
    
    @BeforeAll
    public static void initializeReflectionsLibrary() {
        
        reflections = new Reflections("com.baeldung.checkinterface");
    }

    @Test
    public void whenUsingReflectionGetInterfaces_thenDirectlyImplementedInterfaceIsFound() {
        
        ChildClass2 childClass2 = new ChildClass2();        
        List<Class<?>> interfaces = Arrays.asList(childClass2.getClass().getInterfaces());
        
        assertEquals(1, interfaces.size());
        assertTrue(interfaces.contains(ChildInterface2.class));
    }
    
    @Test
    public void whenUsingReflectionGetInterfaces_thenParentInterfaceIsNotFound() {
        
        ChildClass2 childClass2 = new ChildClass2();        
        List<Class<?>> interfaces = Arrays.asList(childClass2.getClass().getInterfaces());
        
        assertFalse(interfaces.contains(MasterInterface.class));
    }
    
    @Test
    public void whenUsingReflectionGetInterfacesRecursively_thenParentInterfaceIsFound() {
        
        Set<Class<?>> interfaces = getAllExtendedOrImplementedInterfacesRecursively(ChildClass2.class);
        
        assertTrue(interfaces.contains(ChildInterface2.class));
        assertTrue(interfaces.contains(MasterInterface.class));
    }
    
    @Test
    public void whenUsingReflectionIsAssignableFrom_thenDirectlyImplementedInterfaceIsFound() {
        
        ChildClass2 childClass2 = new ChildClass2();        
        
        assertTrue(ChildInterface2.class.isAssignableFrom(childClass2.getClass()));
    }
    
    @Test
    public void whenUsingReflectionIsAssignableFrom_thenParentInterfaceIsFound() {
        
        ChildClass2 childClass2 = new ChildClass2();        
        
        assertTrue(MasterInterface.class.isAssignableFrom(childClass2.getClass()));
    }
    
    @Test
    public void whenUsingReflectionIsInstance_thenDirectlyImplementedInterfaceIsFound() {
        
        ChildClass2 childClass2 = new ChildClass2();        
        
        assertTrue(ChildInterface2.class.isInstance(childClass2));
    }
    
    @Test
    public void whenUsingReflectionIsInstance_thenParentInterfaceIsFound() {
        
        ChildClass2 childClass2 = new ChildClass2();        
        
        assertTrue(MasterInterface.class.isInstance(childClass2));
    }
    
    @Test
    public void whenUsingReflectionInstanceOf_thenDirectlyImplementedInterfaceIsFound() {
        
        ChildClass2 childClass2 = new ChildClass2();        
        
        assertTrue(childClass2 instanceof ChildInterface2);
    }
    
    @Test
    public void whenUsingReflectionInstanceOf_thenParentInterfaceIsFound() {
        
        ChildClass2 childClass2 = new ChildClass2();        
        
        assertTrue(childClass2 instanceof MasterInterface);
    }
    
    @Test
    public void whenUsingCommons_thenDirectlyImplementedInterfaceIsFound() {
        
        ChildClass2 childClass2 = new ChildClass2();        
        List<Class<?>> interfaces = ClassUtils.getAllInterfaces(childClass2.getClass());
        
        assertTrue(interfaces.contains(ChildInterface2.class));
    }
    
    @Test
    public void whenUsingCommons_thenParentInterfaceIsFound() {
        
        ChildClass2 childClass2 = new ChildClass2();        
        List<Class<?>> interfaces = ClassUtils.getAllInterfaces(childClass2.getClass());
        
        assertTrue(interfaces.contains(MasterInterface.class));
    }
    
    @Test
    public void whenUsingReflections_thenDirectlyImplementedInterfaceIsFound() {
        
        ChildClass2 childClass2 = new ChildClass2();        
        Set<Class<?>> interfaces = reflections.get(ReflectionUtils.Interfaces.of(childClass2.getClass()));
        
        assertTrue(interfaces.contains(ChildInterface2.class));
    }
    
    @Test
    public void whenUsingReflections_thenParentInterfaceIsFound() {
        
        ChildClass2 childClass2 = new ChildClass2();        
        Set<Class<?>> interfaces = reflections.get(ReflectionUtils.Interfaces.of(childClass2.getClass()));
        
        assertTrue(interfaces.contains(MasterInterface.class));
    }
    
    static Set<Class<?>> getAllExtendedOrImplementedInterfacesRecursively(Class<?> clazz) { 
        
        Set<Class<?>> res = new HashSet<Class<?>>(); 
        Class<?>[] interfaces = clazz.getInterfaces(); 
        
        if (interfaces.length > 0) { 
            res.addAll(Arrays.asList(interfaces)); 
            for (Class<?> interfaze : interfaces) { 
                res.addAll(getAllExtendedOrImplementedInterfacesRecursively(interfaze)); 
                } 
            } 
        
        return res; 
       }
}
