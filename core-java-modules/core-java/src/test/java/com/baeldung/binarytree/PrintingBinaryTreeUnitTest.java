package com.baeldung.binarytree;

import org.junit.Before;
import org.junit.Test;

public class PrintingBinaryTreeUnitTest {

    private BinaryTreeModel balanced;
    private BinaryTreeModel leftUnbalanced;
    private BinaryTreeModel rightUnbalanced;

    @Before
    public void setup() {
        balanced = createBalancedTree();
        leftUnbalanced = createLeftUnbalancedTree();
        rightUnbalanced = createRightUnbalancedTree();
    }

    private BinaryTreeModel createBalancedTree() {

        BinaryTreeModel root = new BinaryTreeModel("root");

        BinaryTreeModel node1 = new BinaryTreeModel("node1");
        BinaryTreeModel node2 = new BinaryTreeModel("node2");
        root.setLeft(node1);
        root.setRight(node2);

        BinaryTreeModel node3 = new BinaryTreeModel("node3");
        BinaryTreeModel node4 = new BinaryTreeModel("node4");
        node1.setLeft(node3);
        node1.setRight(node4);

        BinaryTreeModel node5 = new BinaryTreeModel("node5");
        BinaryTreeModel node6 = new BinaryTreeModel("node6");
        node2.setLeft(node5);
        node2.setRight(node6);

        BinaryTreeModel node7 = new BinaryTreeModel("node7");
        node3.setLeft(node7);

        BinaryTreeModel node8 = new BinaryTreeModel("node8");
        BinaryTreeModel node9 = new BinaryTreeModel("node9");
        node7.setLeft(node8);
        node7.setRight(node9);

        return root;
    }

    private BinaryTreeModel createLeftUnbalancedTree() {

        BinaryTreeModel root = new BinaryTreeModel("left");

        BinaryTreeModel node1 = new BinaryTreeModel("node1");
        BinaryTreeModel node2 = new BinaryTreeModel("node2");
        root.setLeft(node1);
        root.setRight(node2);
        
        BinaryTreeModel node3 = new BinaryTreeModel("node3");
        node1.setLeft(node3);
        
        BinaryTreeModel node4 = new BinaryTreeModel("node4");
        node3.setLeft(node4);
        
        BinaryTreeModel node5 = new BinaryTreeModel("node5");
        node4.setLeft(node5);
        
        BinaryTreeModel node6 = new BinaryTreeModel("node6");
        node5.setLeft(node6);
        
        BinaryTreeModel node7 = new BinaryTreeModel("node7");
        node6.setLeft(node7);
        
        BinaryTreeModel node8 = new BinaryTreeModel("node8");
        node7.setLeft(node8);

        return root;
    }

    private BinaryTreeModel createRightUnbalancedTree() {

        BinaryTreeModel root = new BinaryTreeModel("right");

        BinaryTreeModel node1 = new BinaryTreeModel("node1");
        BinaryTreeModel node2 = new BinaryTreeModel("node2");
        root.setLeft(node1);
        root.setRight(node2);
        
        BinaryTreeModel node3 = new BinaryTreeModel("node3");
        node2.setRight(node3);
        
        BinaryTreeModel node4 = new BinaryTreeModel("node4");
        node3.setRight(node4);
        
        BinaryTreeModel node5 = new BinaryTreeModel("node5");
        node4.setRight(node5);
        
        BinaryTreeModel node6 = new BinaryTreeModel("node6");
        node5.setRight(node6);
        
        BinaryTreeModel node7 = new BinaryTreeModel("node7");
        node6.setRight(node7);
        
        BinaryTreeModel node8 = new BinaryTreeModel("node8");
        node7.setRight(node8);

        return root;
    }

    @Test
    public void givenBinaryTreeModelBalanced_whenPrintWithBinaryTreePrinter() {
        new BinaryTreePrinter(balanced).print();
    }

    @Test
    public void givenBinaryTreeModelLeftUnbalanced_whenPrintWithBinaryTreePrinter() {
        new BinaryTreePrinter(leftUnbalanced).print();
    }

    @Test
    public void givenBinaryTreeModelRightUnbalanced_whenPrintWithBinaryTreePrinter() {
        new BinaryTreePrinter(rightUnbalanced).print();
    }

}
