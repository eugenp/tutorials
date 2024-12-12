package com.baeldung.collections;

 
import static org.junit.jupiter.api.Assertions.assertEquals;
import org.junit.jupiter.api.Test; 
import java.util.Arrays; 
import java.util.ArrayList;

public class SequencedArrayListUnitTest {

    @Test
    public void givenSequencedArrayList_whenGetFirst_thenFirstElementReturnedCorrectly() {

        ArrayList<Integer> arrayList = new ArrayList<Integer>(Arrays.asList(3,1,2));

        Integer expectedElement=3;
        assertEquals(expectedElement, arrayList.getFirst());
    } 

    @Test
    public void givenSequencedArrayList_whenGetLast_thenLastElementReturnedCorrectly() {
       
        ArrayList<Integer> arrayList = new ArrayList<Integer>(Arrays.asList(3,1,2));

        Integer expectedElement=2;
        assertEquals(expectedElement, arrayList.getLast());
    } 

    @Test
    public void givenSequencedArrayList_whenAddFirst_thenFirstElementAddedCorrectly() {

        ArrayList<Integer> arrayList = new ArrayList<Integer>(Arrays.asList(3,1,2));
        arrayList.addFirst(4);
     
        Integer expectedElement=4;
        assertEquals(expectedElement, arrayList.getFirst());
    } 

    @Test
    public void givenSequencedArrayList_whenAddLast_thenLastElementAddedCorrectly() {
       
        ArrayList<Integer> arrayList = new ArrayList<Integer>(Arrays.asList(3,1,2));
        arrayList.addLast(5);
    
        Integer expectedElement=5;
        assertEquals(expectedElement, arrayList.getLast());
    } 

    @Test
    public void givenSequencedArrayList_whenRemoveFirst_thenFirstElementRemovedCorrectly() {

        ArrayList<Integer> arrayList = new ArrayList<Integer>(Arrays.asList(3,1,2));
       
        Integer expectedElement=3;
        assertEquals(expectedElement, arrayList.removeFirst());
    } 

    @Test
    public void givenSequencedArrayList_whenRemoveLast_thenLastElementRemovedCorrectly() {
       
        ArrayList<Integer> arrayList = new ArrayList<Integer>(Arrays.asList(3,1,2));
     
        Integer expectedElement=2;
        assertEquals(expectedElement, arrayList.removeLast());
    } 

}
