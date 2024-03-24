package com.baeldung.copy;

import static org.junit.Assert.assertEquals;
import static org.junit.Assert.assertNotEquals;

import org.junit.Before;
import org.junit.Test;

import com.baeldung.copy.object.Backpack;
import com.baeldung.copy.object.Food;
import com.baeldung.copy.object.LunchBox;

public class BackpackCopyServiceTest {

    private Backpack backpack;
    private LunchBox lunchBox;
    private Food food;

    @Before
    public void setup() {
        food = new Food("sweet");
        lunchBox = new LunchBox();
        lunchBox.addFood(food);
        backpack = new Backpack(5, lunchBox);
    }

    @Test
    public void whenShallowCopy_thenLunchBoxEquality() {
        Backpack backpackCopy = BackpackCopyService.shallowCopy(backpack);
        assertNotEquals(backpack, backpackCopy);
        assertEquals(backpack.getLunchBox(), backpackCopy.getLunchBox());
        assertEquals(food, backpackCopy.getLunchBox()
            .grabFood());
        assertEquals("sweet", backpackCopy.getLunchBox()
            .grabFood()
            .getTaste());
        backpack.getLunchBox().replaceFood(new Food("tart"));
        assertEquals("tart", backpackCopy.getLunchBox()
            .grabFood()
            .getTaste());
    }

    @Test
    public void whenDeepCopy_thenLunchboxInequality() {
        Backpack backpackCopy = BackpackCopyService.deepCopy(backpack);
        assertNotEquals(backpack, backpackCopy);
        assertNotEquals(backpack.getLunchBox(), backpackCopy.getLunchBox());
        assertNotEquals(food, backpackCopy.getLunchBox()
            .grabFood());
        backpack.getLunchBox().replaceFood(new Food("tart"));
        assertEquals("sweet", backpackCopy.getLunchBox()
            .grabFood()
            .getTaste());
    }
}
