package com.baeldung.immutables;

import static org.junit.Assert.assertEquals;

import java.util.ArrayList;
import java.util.List;

import org.junit.Assert;
import org.junit.Test;

public class ImmutableCollectionsExampleTest {

	@Test
    public void givenUnmodifiableCollection_whenAddNewItem_thenThrowUnsupportedOperationException() {
		List<String> list = new ArrayList<>();
		list.add("One");
		list.add("Two");
		list.add("Three");
		
		ImmutableCollectionsExample ice = new ImmutableCollectionsExample();
        List<String> unmodifiableList = ice.createUnmodifiableList(list);
        
        try {
        	unmodifiableList.add("Four");
        }catch(Exception e) {
        	assertEquals(e.getClass().getName(), UnsupportedOperationException.class.getName());
        }
    }
	
	@Test
    public void givenUnmodifiableCollection_whenAddNewItemUsingOrigianReference_thenAddSuccessful() {
		List<String> list = new ArrayList<>();
		list.add("One");
		list.add("Two");
		list.add("Three");
		
		ImmutableCollectionsExample ice = new ImmutableCollectionsExample();
        List<String> unmodifiableList = ice.createUnmodifiableList(list);
        
        try {
        	unmodifiableList.add("Four");
        }catch(Exception e) {
        	assertEquals(e.getClass().getName(), UnsupportedOperationException.class.getName());
        }
        
        list.add("Four");
        assertEquals(unmodifiableList, list);
        Assert.assertTrue(unmodifiableList.contains("Four"));
    }
	
	@Test
    public void givenImmutableCollection_whenAddNewItem_thenThrowUnsupportedOperationException() {
		ImmutableCollectionsExample ice = new ImmutableCollectionsExample();
        List<String> immutableList = ice.createImmuteableList();
        
        try {
        	immutableList.add("Four");
        }catch(Exception e) {
        	assertEquals(e.getClass().getName(), UnsupportedOperationException.class.getName());
        }
    }
	
	@Test
    public void givenUnmodifiableCollection_whenAddNewItemUsingOrigianlReference_thenThrowUnsupportedOperationException() {
		List<String> list = new ArrayList<>();
		list.add("One");
		list.add("Two");
		list.add("Three");
		
		ImmutableCollectionsExample ice = new ImmutableCollectionsExample();
        List<String> immutableList = ice.createImmuteableList(list);
        
        try {
        	immutableList.add("Four");
        }catch(Exception e) {
        	assertEquals(e.getClass().getName(), UnsupportedOperationException.class.getName());
        }
        
        list.add("Four");
        Assert.assertNotEquals(immutableList, list);
        Assert.assertFalse(immutableList.contains("Four"));
    }
}
