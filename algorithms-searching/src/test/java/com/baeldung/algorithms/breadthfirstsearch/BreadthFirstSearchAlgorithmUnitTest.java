package com.baeldung.algorithms.breadthfirstsearch;

import org.junit.jupiter.api.Test;

import static org.assertj.core.api.Assertions.assertThat;

class BreadthFirstSearchAlgorithmUnitTest {

    private Tree<Integer> root;
    private Tree<Integer> rootFirstChild;
    private Tree<Integer> depthMostChild;
    private Tree<Integer> rootSecondChild;

    private Node<Integer> start;
    private Node<Integer> firstNeighbor;
    private Node<Integer> firstNeighborNeighbor;
    private Node<Integer> secondNeighbor;

    @Test
    void givenTree_whenSearchTen_thenRoot() {
        initTree();
        assertThat(BreadthFirstSearchAlgorithm.search(10, root)).isPresent().contains(root);
    }

    @Test
    void givenTree_whenSearchThree_thenDepthMostValue() {
        initTree();
        assertThat(BreadthFirstSearchAlgorithm.search(3, root)).isPresent().contains(depthMostChild);
    }

    @Test
    void givenTree_whenSearchFour_thenRootSecondChild() {
        initTree();
        assertThat(BreadthFirstSearchAlgorithm.search(4, root)).isPresent().contains(rootSecondChild);
    }

    @Test
    void givenTree_whenSearchFive_thenNotFound() {
        initTree();
        assertThat(BreadthFirstSearchAlgorithm.search(5, root)).isEmpty();
    }

    private void initTree() {
        root = Tree.of(10);
        rootFirstChild = root.addChild(2);
        depthMostChild = rootFirstChild.addChild(3);
        rootSecondChild = root.addChild(4);
    }

    @Test
    void givenNode_whenSearchTen_thenStart() {
        initNode();
        assertThat(BreadthFirstSearchAlgorithm.search(10, firstNeighborNeighbor)).isPresent().contains(start);
    }

    @Test
    void givenNode_whenSearchThree_thenNeighborNeighbor() {
        initNode();
        assertThat(BreadthFirstSearchAlgorithm.search(3, firstNeighborNeighbor)).isPresent().contains(firstNeighborNeighbor);
    }

    @Test
    void givenNode_whenSearchFour_thenSecondNeighbor() {
        initNode();
        assertThat(BreadthFirstSearchAlgorithm.search(4, firstNeighborNeighbor)).isPresent().contains(secondNeighbor);
    }

    @Test
    void givenNode_whenSearchFive_thenNotFound() {
        initNode();
        assertThat(BreadthFirstSearchAlgorithm.search(5, firstNeighborNeighbor)).isEmpty();
    }

    private void initNode() {
        start = new Node<>(10);
        firstNeighbor = new Node<>(2);
        start.connect(firstNeighbor);

        firstNeighborNeighbor = new Node<>(3);
        firstNeighbor.connect(firstNeighborNeighbor);
        firstNeighborNeighbor.connect(start);

        secondNeighbor = new Node<>(4);
        start.connect(secondNeighbor);
    }
}