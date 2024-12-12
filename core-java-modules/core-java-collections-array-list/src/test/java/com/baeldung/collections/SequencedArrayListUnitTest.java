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

}
