package com.baeldung.recursion;

import org.junit.Assert;
import org.junit.Test;

public class RecursionExampleUnitTest {
    
    RecursionExample recursion = new RecursionExample();

    @Test
    public void testPowerOf10() {
        int p0 = recursion.powerOf10(0);
        int p1 = recursion.powerOf10(1);
        int p4 = recursion.powerOf10(4);
        
        Assert.assertEquals(1, p0);
        Assert.assertEquals(10, p1);
        Assert.assertEquals(10000, p4);
    }
    
    @Test
    public void testFibonacci() {
        int n0 = recursion.fibonacci(0);
        int n1 = recursion.fibonacci(1);
        int n7 = recursion.fibonacci(7);
        
        Assert.assertEquals(0, n0);
        Assert.assertEquals(1, n1);
        Assert.assertEquals(13, n7);
    }
    
    @Test
    public void testToBinary() {
        String b0 = recursion.toBinary(0);
        String b1 = recursion.toBinary(1);
        String b10 = recursion.toBinary(10);
        
        Assert.assertEquals("0", b0);
        Assert.assertEquals("1", b1);
        Assert.assertEquals("1010", b10);
    }
    
    @Test
    public void testCalculateTreeHeight() {
        BinaryNode root = new BinaryNode(1);
        root.setLeft(new BinaryNode(1));
        root.setRight(new BinaryNode(1));
        
        root.getLeft().setLeft(new BinaryNode(1));
        root.getLeft().getLeft().setRight(new BinaryNode(1));
        root.getLeft().getLeft().getRight().setLeft(new BinaryNode(1));
        
        root.getRight().setLeft(new BinaryNode(1));
        root.getRight().getLeft().setRight(new BinaryNode(1));
        
        int height = recursion.calculateTreeHeight(root);
        
        Assert.assertEquals(4, height);
    }

}
