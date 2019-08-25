package com.baeldung.prototype;

import org.junit.Before;
import org.junit.Test;

import java.util.HashMap;
import java.util.Map;

import static org.junit.Assert.assertNotSame;

public class PrototypeIntegrationTest {

    private ItemManager itemManager;
    private Rabbit rabbit;
    private Duck duck;

    @Before
    public void init() {
        Map<String, Item> itemMap = new HashMap<>();

        rabbit = new Rabbit("white", "Metadata to create Rabbit");
        itemMap.put(Rabbit.class.getName(), rabbit);

        duck = new Duck("Yellow", "Metadata to create Duck");
        itemMap.put(Duck.class.getName(), duck);

        itemManager = new ItemManager(itemMap);
    }

    @Test
    public void givenDuckObjectWhenCloneIsInvokedThenReturnClonedObject() {
        Duck blueDuck = (Duck) itemManager.getItem(Duck.class.getName());
        blueDuck.setColor("blue");

        assertNotSame(duck, blueDuck);
    }

    @Test
    public void givenRabbitObjectWhenCloneIsInvokedThenReturnClonedObject() {
        Rabbit greyRabbit = (Rabbit) itemManager.getItem(Rabbit.class.getName());
        greyRabbit.setColor("grey");

        assertNotSame(rabbit, greyRabbit);
    }

}
