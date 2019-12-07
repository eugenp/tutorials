package com.baeldung.printbinarytree;

import static org.junit.jupiter.api.Assertions.assertEquals;

import java.io.ByteArrayOutputStream;
import java.io.OutputStream;
import java.io.PrintStream;

import org.junit.After;
import org.junit.Before;
import org.junit.Test;

import com.baeldung.printbinarytree.BinaryTreeModel;
import com.baeldung.printbinarytree.BinaryTreePrinter;

public class PrintingBinaryTreeModelUnitTest {

    private BinaryTreeModel balanced;
    private BinaryTreeModel leftSkewed;
    private BinaryTreeModel rightSkewed;
    
    private OutputStream output;

    @Before
    public void setup() {
        balanced = createBalancedTree();
        leftSkewed = createLeftUnbalancedTree();
        rightSkewed = createRightUnbalancedTree();
        
        output = new ByteArrayOutputStream();
        System.setOut(new PrintStream(output));
    }
    
    @After
    public void tearDown() {
        System.setOut(System.out);
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
         
        node2.setLeft(new BinaryTreeModel("node5"));
        node2.setRight(new BinaryTreeModel("node6"));
         
        BinaryTreeModel node7 = new BinaryTreeModel("node7");
        node3.setLeft(node7);
        node7.setLeft(new BinaryTreeModel("node8"));
        node7.setRight(new BinaryTreeModel("node9"));
        
        return root;
    }

    private BinaryTreeModel createLeftUnbalancedTree() {

        BinaryTreeModel root = new BinaryTreeModel("root");

        BinaryTreeModel node1 = new BinaryTreeModel("node1");
        root.setLeft(node1);
        root.setRight(new BinaryTreeModel("node2"));
        
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
        
        node7.setLeft(new BinaryTreeModel("node8"));

        return root;
    }

    private BinaryTreeModel createRightUnbalancedTree() {

        BinaryTreeModel root = new BinaryTreeModel("root");

        BinaryTreeModel node2 = new BinaryTreeModel("node2");
        root.setLeft(new BinaryTreeModel("node1"));
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
        
        node7.setRight(new BinaryTreeModel("node8"));

        return root;
    }

    @Test
    public void givenBinaryTreeModelBalanced_whenPrintWithBinaryTreePrinter_thenProduceCorrectOutput() {
        
        StringBuilder expected = new StringBuilder();
        expected.append("root").append("\n");
        expected.append("├──node1").append("\n");
        expected.append("│  ├──node3").append("\n");
        expected.append("│  │  └──node7").append("\n");
        expected.append("│  │     ├──node8").append("\n");
        expected.append("│  │     └──node9").append("\n");
        expected.append("│  └──node4").append("\n");
        expected.append("└──node2").append("\n");
        expected.append("   ├──node5").append("\n");
        expected.append("   └──node6");
        
        new BinaryTreePrinter(balanced).print(System.out);
        
        assertEquals(expected.toString(), output.toString());
    }

    @Test
    public void givenBinaryTreeModelLeftUnbalanced_whenPrintWithBinaryTreePrinter_thenProduceCorrectOutput() {
        
        StringBuilder expected = new StringBuilder();
        expected.append("root").append("\n");
        expected.append("├──node1").append("\n");
        expected.append("│  └──node3").append("\n");
        expected.append("│     └──node4").append("\n");
        expected.append("│        └──node5").append("\n");
        expected.append("│           └──node6").append("\n");
        expected.append("│              └──node7").append("\n");
        expected.append("│                 └──node8").append("\n");
        expected.append("└──node2");
        
        new BinaryTreePrinter(leftSkewed).print(System.out);
        
        assertEquals(expected.toString(), output.toString());
    }

    @Test
    public void givenBinaryTreeModelRightUnbalanced_whenPrintWithBinaryTreePrinter_thenProduceCorrectOutput() {
        
        StringBuilder expected = new StringBuilder();
        expected.append("root").append("\n");
        expected.append("├──node1").append("\n");
        expected.append("└──node2").append("\n");
        expected.append("   └──node3").append("\n");
        expected.append("      └──node4").append("\n");
        expected.append("         └──node5").append("\n");
        expected.append("            └──node6").append("\n");
        expected.append("               └──node7").append("\n");
        expected.append("                  └──node8");
        
        new BinaryTreePrinter(rightSkewed).print(System.out);
        
        assertEquals(expected.toString(), output.toString());
    }

}
