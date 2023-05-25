package com.baeldung.decorator;

import static org.junit.Assert.assertEquals;

import org.junit.Test;

public class DecoratorPatternIntegrationTest {
    @Test
    public void givenDecoratorPattern_WhenDecoratorsInjectedAtRuntime_thenConfigSuccess() {
        ChristmasTree tree1 = new Garland(new ChristmasTreeImpl());
        assertEquals(tree1.decorate(), 
          "Christmas tree with Garland");
         
        ChristmasTree tree2 = new BubbleLights(
          new Garland(new Garland(new ChristmasTreeImpl())));
        assertEquals(tree2.decorate(), 
          "Christmas tree with Garland with Garland with Bubble Lights");
    }
}

