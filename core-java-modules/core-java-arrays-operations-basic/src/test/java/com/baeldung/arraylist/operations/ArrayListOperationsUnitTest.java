package com.baeldung.arraylist.operations;

import java.util.ArrayList;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import static org.assertj.core.api.Assertions.assertThat;

public class ArrayListOperationsUnitTest {

    private ArrayList<Integer> anArrayList;

    @BeforeEach
    public void setupDefaults() {
        anArrayList = new ArrayList<>();
        anArrayList.add(2);
        anArrayList.add(3);
        anArrayList.add(4);
    }

    @Test
    public void whenGetAnIntegerElementCalled_thenReturnTheIntegerElement() {
        Integer output = ArrayListOperations.getAnIntegerElement(anArrayList, 1);

        assertThat(output).isEqualTo(3);
    }

    @Test
    public void whenModifyAnIntegerElementCalled_thenModifyTheIntegerElement() {
        ArrayListOperations.modifyAnIntegerElement(anArrayList, 2, 5);
        Integer output = ArrayListOperations.getAnIntegerElement(anArrayList, 2);

        assertThat(output).isEqualTo(5);
    }

    @Test
    public void whenAppendAnIntegerElementCalled_thenTheIntegerElementIsAppendedToArrayList() {
        ArrayListOperations.appendAnIntegerElement(anArrayList, 6);
        Integer output = ArrayListOperations.getAnIntegerElement(anArrayList, anArrayList.size() - 1);

        assertThat(output).isEqualTo(6);
    }

    @Test
    public void whenInsertAnIntegerAtIndexCalled_thenTheIntegerElementIsInseredToArrayList() {
        ArrayListOperations.insertAnIntegerElementAtIndex(anArrayList, 1, 10);
        Integer output = ArrayListOperations.getAnIntegerElement(anArrayList, 1);

        assertThat(output).isEqualTo(10);
    }
}