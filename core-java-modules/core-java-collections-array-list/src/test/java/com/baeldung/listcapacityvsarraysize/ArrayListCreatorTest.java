package com.baeldung.listcapacityvsarraysize;

import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;

import java.util.ArrayList;

import static org.junit.jupiter.api.Assertions.*;

class ArrayListCreatorTest {

    private ArrayListCreator<Integer> arrayListCreator;

    @BeforeEach
    void setUp() {
        arrayListCreator = new ArrayListCreator<>();
    }

    @Test
    void givenValidCapacityOfList_whenCreateListInvoked_thenCreateNewArrayListWithGivenCapacity() {
        assertNotNull(arrayListCreator.createArrayList(100));
    }

    @Test
    void givenInvalidCapacityOfList_whenCreateListInvoked_thenThrowException() {
        assertThrows(IllegalArgumentException.class, () -> arrayListCreator.createArrayList(-1));
    }

    @Test
    void givenValidCapacityOfList_whenCreateListInvoked_thenCreateNewArrayListWithSizeZero() {
        ArrayList<Integer> list = arrayListCreator.createArrayList(100);

        assertEquals(0, list.size());
    }
}