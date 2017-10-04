package com.baeldung.fj;

import static org.junit.Assert.assertEquals;

import org.junit.Test;

import fj.F;
import fj.data.Array;
import fj.data.List;
import fj.data.Option;
import fj.function.Characters;
import fj.function.Integers;

public class FunctionalJavaTest {
	
	public static final F<Integer, Boolean> isEven = i -> i % 2 == 0;
	
    @Test
    public void calculateEvenNumbers_givenIntList_returnTrue() {
    	List<Integer> fList = List.list(3, 4, 5, 6);
        List<Boolean> evenList = fList.map(isEven);
        List<Boolean> evenListTrueResult = List.list(false, true, false, true);
        List<Boolean> evenListFalseResult = List.list(true, false, false, true);
        assertEquals(evenList.equals(evenListTrueResult), true);
        assertEquals(evenList.equals(evenListFalseResult), false);
    }
    
    @Test
    public void mapList_givenIntList_returnResult() {
    	List<Integer> fList = List.list(3, 4, 5, 6);
    	fList = fList.map(i -> i + 100);
    	List<Integer> resultList = List.list(103, 104, 105, 106);
    	List<Integer> falseResultList = List.list(15, 504, 105, 106);
    	assertEquals(fList.equals(resultList), true);
    	assertEquals(fList.equals(falseResultList), false);
    }
    
    @Test
    public void filterList_givenIntList_returnResult() {
    	Array<Integer> array = Array.array(3, 4, 5, 6);
    	Array<Integer> filteredArray = array.filter(Integers.even);
    	Array<Integer> result = Array.array(4, 6);
    	Array<Integer> wrongResult = Array.array(3, 5);
    	assertEquals(filteredArray.equals(result), true);
    	assertEquals(filteredArray.equals(wrongResult), false);
    }
    
    @Test
    public void checkForLowerCase_givenStringArray_returnResult() {
    	Array<String> array = Array.array("Welcome", "To", "baeldung");
    	Array<String> array2 = Array.array("Welcome", "To", "Baeldung");
        Boolean isExist = array.exists(s -> List.fromString(s).forall(Characters.isLowerCase));
        Boolean isExist2 = array2.exists(s -> List.fromString(s).forall(Characters.isLowerCase));
        assertEquals(isExist, true);
        assertEquals(isExist2, false);
    }
   
    @Test
    public void checkOptions_givenOptions_returnResult() {
        Option<Integer> n1 = Option.some(1);
        Option<Integer> n2 = Option.some(2);

        F<Integer, Option<Integer>> f1 = i -> i % 2 == 0 ? Option.some(i + 100) : Option.none();

        Option<Integer> result1 = n1.bind(f1);
        Option<Integer> result2 = n2.bind(f1);
        
        assertEquals(result1, Option.none());
        assertEquals(result2, Option.some(102));
    }
    
    @Test
    public void foldLeft_givenArray_returnResult() {
        Array<Integer> intArray = Array.array(17, 44, 67, 2, 22, 80, 1, 27);
        int sum = intArray.foldLeft(Integers.add, 0);
        assertEquals(sum, 260);
    }
    
}
