package com.baeldung.recursion;

import org.junit.Assert;
import org.junit.Test;

/**
 * 测试：递归
 *
 针对树，理解下面的概念：
 （1）节点n的高度 : n节点到叶子节点所有路径上包含节点个数的最大值。叶子节点的高度为1，往上节点的高度依次递增。
 （2）节点n的深度 : 从根节点到节点n唯一的路径的长，根节点深度为1。
 （3）层数：根节点为第一层，往下一次递增。

 注意1：树中节点的最大层数称之为树的深度或者高度，所以在基数为1时树的深度=树的高度=最大层数

 注意2：但是节点的深度和高度并没有必然的关系

 （4）节点的度：节点拥有的子树的个数，度为0的节点称之为叶子节点
 树的度：是树内所有节点度的最大值
 树的深度：树内所有节点深度的最大值，也就是所有叶子节点深度的最大值，也就是树的层数
 树的高度：树内所有节点高度的最大值，也就是根节点的高度，也就是数的层数
 *
 */
public class RecursionExampleUnitTest {
    
    RecursionExample recursion = new RecursionExample();

    /**
     * 测试：10的n次幂
     */
    @Test
    public void testPowerOf10() {
        int p0 = recursion.powerOf10(0);
        int p1 = recursion.powerOf10(1);
        int p4 = recursion.powerOf10(4);
        
        Assert.assertEquals(1, p0);
        Assert.assertEquals(10, p1);
        Assert.assertEquals(10000, p4);
    }

    /**
     * 测试：第n个斐波那契数
     */
    @Test
    public void testFibonacci() {
        int n0 = recursion.fibonacci(0);
        int n1 = recursion.fibonacci(1);
        int n7 = recursion.fibonacci(7);
        
        Assert.assertEquals(0, n0);
        Assert.assertEquals(1, n1);
        Assert.assertEquals(13, n7);
    }

    /**
     * 测试：十进制数=>二进制数
     */
    @Test
    public void testToBinary() {
        String b0 = recursion.toBinary(0);
        String b1 = recursion.toBinary(1);
        String b10 = recursion.toBinary(10);
        
        Assert.assertEquals("0", b0);
        Assert.assertEquals("1", b1);
        Assert.assertEquals("1010", b10);
    }

    /**
     * 测试：树高度
     *        1
     *     /    \
     *    1     1
     *   /     /
     *  1     1
     *   \     \
     *   1     1
     *  /
     * 1
     *
     */
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
