package com.baeldung.prototype;

import static org.junit.jupiter.api.Assertions.assertEquals;

import java.util.Arrays;
import java.util.List;
import static java.util.stream.Collectors.toList;

import org.junit.jupiter.api.Test;

public class TreePrototypeUnitTest {

    @Test
    public void givenAPlasticTreePrototypeWhenClonedThenCreateA_Clone() {
        double mass = 10.0;
        double height = 3.7;
        Position position = new Position(3, 7);
        Position otherPosition = new Position(4, 8);

        PlasticTree plasticTree = new PlasticTree(mass, height);
        plasticTree.setPosition(position);
        PlasticTree anotherPlasticTree = (PlasticTree) plasticTree.copy();
        anotherPlasticTree.setPosition(otherPosition);

        assertEquals(position, plasticTree.getPosition());
        assertEquals(otherPosition, anotherPlasticTree.getPosition());

    }

    @Test
    public void givenAPineTreePrototypeWhenClonedThenCreateA_Clone() {
        double mass = 10.0;
        double height = 3.7;
        Position position = new Position(3, 7);
        Position otherPosition = new Position(4, 8);

        PineTree pineTree = new PineTree(mass, height);
        pineTree.setPosition(position);
        PineTree anotherPineTree = (PineTree) pineTree.copy();
        anotherPineTree.setPosition(otherPosition);

        assertEquals(position, pineTree.getPosition());
        assertEquals(otherPosition, anotherPineTree.getPosition());
    }
    
    @Test
    public void givenA_ListOfTreesWhenClonedThenCreateListOfClones() {
        double mass = 10.0;
        double height = 3.7;
        Position position = new Position(3, 7);
        Position otherPosition = new Position(4, 8);
        
        PlasticTree plasticTree = new PlasticTree(mass, height);
        plasticTree.setPosition(position);
        PineTree pineTree = new PineTree(mass, height);
        pineTree.setPosition(otherPosition);
        
        List<Tree> trees = Arrays.asList(plasticTree, pineTree);
        
        List<Tree> treeClones = trees.stream().map(Tree::copy).collect(toList());
        
        Tree plasticTreeClone = treeClones.get(0);
        
        assertEquals(mass, plasticTreeClone.getMass());
        assertEquals(height, plasticTreeClone.getHeight());
        assertEquals(position, plasticTreeClone.getPosition());
    }
}
