package com.baeldung.algorithms.breadthfirstsearch;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import java.util.*;

public class BreadthFirstSearchAlgorithm {

    private static final Logger LOGGER = LoggerFactory.getLogger(BreadthFirstSearchAlgorithm.class);

    public static <T> Optional<Tree<T>> search(T value, Tree<T> root) {
        Queue<Tree<T>> queue = new ArrayDeque<>();
        queue.add(root);

        return searchTreeQueue(value, queue);
    }

    private static <T> Optional<Tree<T>> searchTreeQueue(T value, Queue<Tree<T>> queue) {
        Tree<T> currentNode;
        while (!queue.isEmpty()) {
            currentNode = queue.remove();
            LOGGER.info("Visited node with value: {}", currentNode.getValue());

            if (currentNode.getValue().equals(value)) {
                return Optional.of(currentNode);
            } else {
                queue.addAll(currentNode.getChildren());
            }
        }

        return Optional.empty();
    }

    public static <T> Optional<Node<T>> search(T value, Node<T> start) {
        Queue<Node<T>> queue = new ArrayDeque<>();
        queue.add(start);

        return searchNodeQueue(value, queue);
    }

    private static <T> Optional<Node<T>> searchNodeQueue(T value, Queue<Node<T>> queue) {
        Node<T> currentNode;
        Set<Node<T>> alreadyVisited = new HashSet<>();

        while (!queue.isEmpty()) {
            currentNode = queue.remove();
            LOGGER.info("Visited node with value: {}", currentNode.getValue());

            if (currentNode.getValue().equals(value)) {
                return Optional.of(currentNode);
            } else {
                alreadyVisited.add(currentNode);
                queue.addAll(currentNode.getNeighbours());
                queue.removeAll(alreadyVisited);
            }
        }

        return Optional.empty();
    }
}
