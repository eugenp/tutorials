package com.baeldung.findanelement;

import static org.junit.Assert.assertNull;
import static org.junit.Assert.assertTrue;
import java.util.ArrayList;
import java.util.List;
import org.junit.Test;

public class FindAnElementTest {

	private static List<Integer> listOfIntegers = new ArrayList<>();
	static {
		listOfIntegers.add(new Integer(0));
		listOfIntegers.add(new Integer(1));
		listOfIntegers.add(new Integer(2));
	}
	
	private static FindElementInAList<Integer> findElementInAList = new FindElementInAList<>();

	@Test
	public void givenElement_whenFoundUsingIndexOf_thenReturnElement() {
		Integer element = new Integer(1);
		Integer foundElement = findElementInAList.findUsingIndexOf(element, listOfIntegers);
		assertTrue(foundElement.equals(element));
	}
	
	@Test
	public void givenElement_whenNotFoundUsingIndexOf_thenReturnNull() {
		Integer foundElement = findElementInAList.findUsingIndexOf(new Integer(5), listOfIntegers);
		assertNull(foundElement);
	}
	
	@Test
	public void givenElement_whenFoundUsingNormalForLoop_thenReturnElement() {
		Integer element = new Integer(1);
		Integer foundElement = findElementInAList.findUsingNormalForLoop(element, listOfIntegers);
		assertTrue(foundElement.equals(element));
	}
	
	@Test
	public void givenElement_whenNotFoundUsingNormalForLoop_thenReturnNull() {
		Integer element = new Integer(5);
		Integer foundElement = findElementInAList.findUsingNormalForLoop(element, listOfIntegers);
		assertNull(foundElement);
	}
	
	@Test
	public void givenElement_whenFoundUsingEnhancedForLoop_thenReturnElement() {
		Integer element = new Integer(1);
		Integer foundElement = findElementInAList.findUsingEnhancedForLoop(element, listOfIntegers);
		assertTrue(foundElement.equals(element));
	}
	
	@Test
	public void givenElement_whenNotFoundUsingEnhancedForLoop_thenReturnNull() {
		Integer element = new Integer(5);
		Integer foundElement = findElementInAList.findUsingEnhancedForLoop(element, listOfIntegers);
		assertNull(foundElement);
	}
	
	@Test
	public void givenElement_whenFoundUsingStream_thenReturnElement() {
		Integer element = new Integer(1);
		Integer foundElement = findElementInAList.findUsingStream(element, listOfIntegers);
		assertTrue(foundElement.equals(element));
	}
	
	@Test
	public void givenElement_whenNotFoundUsingStream_thenReturnNull() {
		Integer element = new Integer(5);
		Integer foundElement = findElementInAList.findUsingStream(element, listOfIntegers);
		assertNull(foundElement);
	}
	
	@Test
	public void givenElement_whenFoundUsingParallelStream_thenReturnElement() {
		Integer element = new Integer(1);
		Integer foundElement = findElementInAList.findUsingParallelStream(element, listOfIntegers);
		assertTrue(foundElement.equals(element));
	}
	
	@Test
	public void givenElement_whenNotFoundUsingParallelStream_thenReturnNull() {
		Integer element = new Integer(5);
		Integer foundElement = findElementInAList.findUsingParallelStream(element, listOfIntegers);
		assertNull(foundElement);
	}
	
	@Test
	public void givenElement_whenFoundUsingGuava_thenReturnElement() {
		Integer element = new Integer(1);
		Integer foundElement = findElementInAList.findUsingGuava(element, listOfIntegers);
		assertTrue(foundElement.equals(element));
	}
	
	@Test
	public void givenElement_whenNotFoundUsingGuava_thenReturnNull() {
		Integer element = new Integer(5);
		Integer foundElement = findElementInAList.findUsingGuava(element, listOfIntegers);
		assertNull(foundElement);
	}
	
	@Test
	public void givenElement_whenFoundUsingApacheCommons_thenReturnElement() {
		Integer element = new Integer(1);
		Integer foundElement = findElementInAList.findUsingApacheCommon(element, listOfIntegers);
		assertTrue(foundElement.equals(element));
	}
	
	@Test
	public void givenElement_whenNotFoundUsingApacheCommons_thenReturnNull() {
		Integer element = new Integer(5);
		Integer foundElement = findElementInAList.findUsingApacheCommon(element, listOfIntegers);
		assertNull(foundElement);
	}

}