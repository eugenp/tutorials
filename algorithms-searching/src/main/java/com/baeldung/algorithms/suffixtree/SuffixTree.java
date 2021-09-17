package com.baeldung.algorithms.suffixtree;

import java.util.ArrayList;
import java.util.List;
import java.util.stream.Collectors;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

public class SuffixTree {
    private static final Logger LOGGER = LoggerFactory.getLogger(SuffixTree.class);
    private static final String WORD_TERMINATION = "$";
    private static final int POSITION_UNDEFINED = -1;
    private Node root;
    private String fullText;

    public SuffixTree(String text) {
        root = new Node("", POSITION_UNDEFINED);
        for (int i = 0; i < text.length(); i++) {
            addSuffix(text.substring(i) + WORD_TERMINATION, i);
        }
        fullText = text;
    }

    public List<String> searchText(String pattern) {
        LOGGER.debug("Searching for pattern \"{}\"", pattern);
        List<String> result = new ArrayList<>();
        List<Node> nodes = getAllNodesInTraversePath(pattern, root, false);

        if (nodes.size() > 0) {
            Node lastNode = nodes.get(nodes.size() - 1);
            if (lastNode != null) {
                List<Integer> positions = getPositions(lastNode);
                positions = positions.stream()
                    .sorted()
                    .collect(Collectors.toList());
                positions.forEach(m -> result.add((markPatternInText(m, pattern))));
            }
        }
        return result;
    }

    private void addSuffix(String suffix, int position) {
        LOGGER.debug(">>>>>>>>>>>> Adding new suffix {}", suffix);
        List<Node> nodes = getAllNodesInTraversePath(suffix, root, true);
        if (nodes.size() == 0) {
            addChildNode(root, suffix, position);
            LOGGER.debug("{}", printTree());
        } else {
            Node lastNode = nodes.remove(nodes.size() - 1);
            String newText = suffix;
            if (nodes.size() > 0) {
                String existingSuffixUptoLastNode = nodes.stream()
                    .map(a -> a.getText())
                    .reduce("", String::concat);

                // Remove prefix from newText already included in parent
                newText = newText.substring(existingSuffixUptoLastNode.length());
            }
            extendNode(lastNode, newText, position);
            LOGGER.debug("{}", printTree());
        }
    }

    private List<Integer> getPositions(Node node) {
        List<Integer> positions = new ArrayList<>();
        if (node.getText()
            .endsWith(WORD_TERMINATION)) {
            positions.add(node.getPosition());
        }
        for (int i = 0; i < node.getChildren()
            .size(); i++) {
            positions.addAll(getPositions(node.getChildren()
                .get(i)));
        }
        return positions;
    }

    private String markPatternInText(Integer startPosition, String pattern) {
        String matchingTextLHS = fullText.substring(0, startPosition);
        String matchingText = fullText.substring(startPosition, startPosition + pattern.length());
        String matchingTextRHS = fullText.substring(startPosition + pattern.length());
        return matchingTextLHS + "[" + matchingText + "]" + matchingTextRHS;
    }

    private void addChildNode(Node parentNode, String text, int position) {
        parentNode.getChildren()
            .add(new Node(text, position));
    }

    private void extendNode(Node node, String newText, int position) {
        String currentText = node.getText();
        String commonPrefix = getLongestCommonPrefix(currentText, newText);

        if (commonPrefix != currentText) {
            String parentText = currentText.substring(0, commonPrefix.length());
            String childText = currentText.substring(commonPrefix.length());
            splitNodeToParentAndChild(node, parentText, childText);
        }

        String remainingText = newText.substring(commonPrefix.length());
        addChildNode(node, remainingText, position);
    }

    private void splitNodeToParentAndChild(Node parentNode, String parentNewText, String childNewText) {
        Node childNode = new Node(childNewText, parentNode.getPosition());

        if (parentNode.getChildren()
            .size() > 0) {
            while (parentNode.getChildren()
                .size() > 0) {
                childNode.getChildren()
                    .add(parentNode.getChildren()
                        .remove(0));
            }
        }

        parentNode.getChildren()
            .add(childNode);
        parentNode.setText(parentNewText);
        parentNode.setPosition(POSITION_UNDEFINED);
    }

    private String getLongestCommonPrefix(String str1, String str2) {
        int compareLength = Math.min(str1.length(), str2.length());
        for (int i = 0; i < compareLength; i++) {
            if (str1.charAt(i) != str2.charAt(i)) {
                return str1.substring(0, i);
            }
        }
        return str1.substring(0, compareLength);
    }

    private List<Node> getAllNodesInTraversePath(String pattern, Node startNode, boolean isAllowPartialMatch) {
        List<Node> nodes = new ArrayList<>();
        for (int i = 0; i < startNode.getChildren()
            .size(); i++) {
            Node currentNode = startNode.getChildren()
                .get(i);
            String nodeText = currentNode.getText();
            if (pattern.charAt(0) == nodeText.charAt(0)) {
                if (isAllowPartialMatch && pattern.length() <= nodeText.length()) {
                    nodes.add(currentNode);
                    return nodes;
                }

                int compareLength = Math.min(nodeText.length(), pattern.length());
                for (int j = 1; j < compareLength; j++) {
                    if (pattern.charAt(j) != nodeText.charAt(j)) {
                        if (isAllowPartialMatch) {
                            nodes.add(currentNode);
                        }
                        return nodes;
                    }
                }

                nodes.add(currentNode);
                if (pattern.length() > compareLength) {
                    List<Node> nodes2 = getAllNodesInTraversePath(pattern.substring(compareLength), currentNode, isAllowPartialMatch);
                    if (nodes2.size() > 0) {
                        nodes.addAll(nodes2);
                    } else if (!isAllowPartialMatch) {
                        nodes.add(null);
                    }
                }
                return nodes;
            }
        }
        return nodes;
    }

    public String printTree() {
        return root.printTree("");
    }
}
