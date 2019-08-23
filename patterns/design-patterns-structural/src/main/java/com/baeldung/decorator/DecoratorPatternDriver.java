package com.baeldung.decorator;

import static com.baeldung.util.LoggerUtil.LOG;

public class DecoratorPatternDriver {

    public static void main(String[] args) {
        // christmas tree with just one Garland
        ChristmasTree tree1 = new Garland(new ChristmasTreeImpl());
        LOG.info(tree1.decorate());

        // christmas tree with two Garlands and one Bubble lights
        ChristmasTree tree2 = new BubbleLights(new Garland(new Garland(new ChristmasTreeImpl())));
        LOG.info(tree2.decorate());

    }

}
