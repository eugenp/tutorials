package com.baeldung.algorithms.breadthfirstsearch;

import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;

import static org.assertj.core.api.Assertions.assertThat;

class BreadthFirstSearchAlgorithmUnitTest {

    private Tree<Integer> root;
    private Tree<Integer> rootFirstChild;
    private Tree<Integer> depthMostChild;
    private Tree<Integer> rootSecondChild;

    private Node<Integer> start;
    private Node<Integer> firstNeighbour;
    private Node<Integer> firstNeighbourNeighbour;
    private Node<Integer> secondNeighbour;

    @BeforeEach
    void beforeEach() {
        initTree();
        initNode();
    }

    private void initTree() {
        root = Tree.of(10);
        rootFirstChild = root.addChild(2);
        depthMostChild = rootFirstChild.addChild(3);
        rootSecondChild = root.addChild(4);
    }

    private void initNode() {
        start = new Node<>(10);
        firstNeighbour = new Node<>(2);
        start.connect(firstNeighbour);

        firstNeighbourNeighbour = new Node<>(3);
        firstNeighbour.connect(firstNeighbourNeighbour);
        firstNeighbourNeighbour.connect(start);

        secondNeighbour = new Node<>(4);
        start.connect(secondNeighbour);
    }

    @Test
    void givenTree_whenSearchTen_thenRoot() {
        assertThat(BreadthFirstSearchAlgorithm.search(10, root)).isPresent().contains(root);
    }

    @Test
    void givenTree_whenSearchThree_thenDepthMostValue() {
        assertThat(BreadthFirstSearchAlgorithm.search(3, root)).isPresent().contains(depthMostChild);
    }

    @Test
    void givenTree_whenSearchFour_thenRootSecondChild() {
        assertThat(BreadthFirstSearchAlgorithm.search(4, root)).isPresent().contains(rootSecondChild);
    }

    @Test
    void givenTree_whenSearchFive_thenNotFound() {
        assertThat(BreadthFirstSearchAlgorithm.search(5, root)).isEmpty();
    }

    @Test
    void givenNode_whenSearchTen_thenStart() {
        assertThat(BreadthFirstSearchAlgorithm.search(10, firstNeighbourNeighbour)).isPresent().contains(start);
    }

    @Test
    void givenNode_whenSearchThree_thenNeighbourNeighbour() {
        assertThat(BreadthFirstSearchAlgorithm.search(3, firstNeighbourNeighbour)).isPresent().contains(firstNeighbourNeighbour);
    }

    @Test
    void givenNode_whenSearchFour_thenSecondNeighbour() {
        assertThat(BreadthFirstSearchAlgorithm.search(4, firstNeighbourNeighbour)).isPresent().contains(secondNeighbour);
    }

    @Test
    void givenNode_whenSearchFive_thenNotFound() {
        assertThat(BreadthFirstSearchAlgorithm.search(5, firstNeighbourNeighbour)).isEmpty();
    }
}