package com.baeldung;

import static org.junit.Assert.assertEquals;

import org.junit.Test;

import com.baeldung.decorator.BubbleLights;
import com.baeldung.decorator.ChristmasTree;
import com.baeldung.decorator.ChristmasTreeImpl;
import com.baeldung.decorator.Garland;

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

