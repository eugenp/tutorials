package com.baeldung.designpatterns;

import static org.junit.Assert.assertEquals;

import org.junit.Test;

import com.baeldung.designpatterns.decorator.BubbleLights;
import com.baeldung.designpatterns.decorator.ChristmasTree;
import com.baeldung.designpatterns.decorator.ChristmasTreeImpl;
import com.baeldung.designpatterns.decorator.Garland;

public class DecoratorPatternIntegrationTest {
    private ChristmasTree tree;
    
    @Test
    public void givenDecoratorPattern_WhenDecoratorsInjectedAtRuntime_thenConfigSuccess() {
        //christmas tree with just one Garland
        tree = new Garland(new ChristmasTreeImpl());
        assertEquals(tree.decorate(), "Christmas tree with Garland");
        
        //christmas tree with two Garlands and one Bubble lights
        tree = new BubbleLights(new Garland(
          new Garland(new ChristmasTreeImpl()))
        );
        assertEquals(tree.decorate(), "Christmas tree with Garland with Garland with Bubble Lights");
    }
}

