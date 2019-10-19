package com.baeldung.prototype;

import static org.junit.jupiter.api.Assertions.assertEquals;

import org.junit.jupiter.api.Test;

public class TreePrototypeUnitTest {

    @Test
    public void givenATreePrototypeWhenClonedThenCreateA_Clone() {
        double mass = 10.0;
        double height = 3.7;
        Position position = new Position(3, 7);
        Position otherPosition = new Position(4, 8);

        Tree tree = new Tree(mass, height);
        tree.setPosition(position);
        Tree anotherTree = tree.clone();
        anotherTree.setPosition(otherPosition);

        assertEquals(position, tree.getPosition());
        assertEquals(otherPosition, anotherTree.getPosition());
    }
}
