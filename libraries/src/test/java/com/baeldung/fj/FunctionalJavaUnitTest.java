package com.baeldung.fj;

import static org.junit.Assert.assertEquals;
import static org.junit.Assert.assertFalse;
import static org.junit.Assert.assertTrue;

import org.junit.Test;

import fj.F;
import fj.Show;
import fj.data.Array;
import fj.data.List;
import fj.data.Option;
import fj.function.Characters;
import fj.function.Integers;

public class FunctionalJavaUnitTest {

    public static final F<Integer, Boolean> isEven = i -> i % 2 == 0;

    public static final Integer timesTwoRegular(Integer i) {
        return i * 2;
    }

    public static final F<Integer, Integer> timesTwo = i -> i * 2;

    public static final F<Integer, Integer> plusOne = i -> i + 1;

    @Test
    public void multiplyNumbers_givenIntList_returnTrue() {
        List<Integer> fList = List.list(1, 2, 3, 4);
        List<Integer> fList1 = fList.map(timesTwo);
        List<Integer> fList2 = fList.map(i -> i * 2);

        assertTrue(fList1.equals(fList2));
    }

    @Test
    public void applyMultipleFunctions_givenIntList_returnFalse() {
        List<Integer> fList = List.list(1, 2, 3, 4);
        List<Integer> fList1 = fList.map(timesTwo).map(plusOne);
        Show.listShow(Show.intShow).println(fList1);
        List<Integer> fList2 = fList.map(plusOne).map(timesTwo);
        Show.listShow(Show.intShow).println(fList2);

        assertFalse(fList1.equals(fList2));
    }

    @Test
    public void calculateEvenNumbers_givenIntList_returnTrue() {
        List<Integer> fList = List.list(3, 4, 5, 6);
        List<Boolean> evenList = fList.map(isEven);
        List<Boolean> evenListTrueResult = List.list(false, true, false, true);

        assertTrue(evenList.equals(evenListTrueResult));
    }

    @Test
    public void mapList_givenIntList_returnResult() {
        List<Integer> fList = List.list(3, 4, 5, 6);
        fList = fList.map(i -> i + 100);
        List<Integer> resultList = List.list(103, 104, 105, 106);

        assertTrue(fList.equals(resultList));
    }

    @Test
    public void filterList_givenIntList_returnResult() {
        Array<Integer> array = Array.array(3, 4, 5, 6);
        Array<Integer> filteredArray = array.filter(isEven);
        Array<Integer> result = Array.array(4, 6);

        assertTrue(filteredArray.equals(result));
    }

    @Test
    public void checkForLowerCase_givenStringArray_returnResult() {
        Array<String> array = Array.array("Welcome", "To", "baeldung");
        assertTrue(array.exists(s -> List.fromString(s).forall(Characters.isLowerCase)));
 
        Array<String> array2 = Array.array("Welcome", "To", "Baeldung");
        assertFalse(array2.exists(s -> List.fromString(s).forall(Characters.isLowerCase)));
 
        assertFalse(array.forall(s -> List.fromString(s).forall(Characters.isLowerCase)));
    }

    @Test
    public void checkOptions_givenOptions_returnResult() {
        Option<Integer> n1 = Option.some(1);
        Option<Integer> n2 = Option.some(2);
        Option<Integer> n3 = Option.none();

        F<Integer, Option<Integer>> function = i -> i % 2 == 0 ? Option.some(i + 100) : Option.none();

        Option<Integer> result1 = n1.bind(function);
        Option<Integer> result2 = n2.bind(function);
        Option<Integer> result3 = n3.bind(function);

        assertEquals(Option.none(), result1);
        assertEquals(Option.some(102), result2);
        assertEquals(Option.none(), result3);
    }

    @Test
    public void foldLeft_givenArray_returnResult() {
        Array<Integer> intArray = Array.array(17, 44, 67, 2, 22, 80, 1, 27);
        int sumAll = intArray.foldLeft(Integers.add, 0);

        assertEquals(260, sumAll);

        int sumEven = intArray.filter(isEven).foldLeft(Integers.add, 0);

        assertEquals(148, sumEven);
    }
    
}
