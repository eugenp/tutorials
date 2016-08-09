package com.baeldung.threadpool;

import java.util.Arrays;
import java.util.HashSet;
import java.util.Set;

public class TreeNode {

    int value;

    Set<TreeNode> children;

    public TreeNode(int value, TreeNode... children) {
        this.value = value;
        this.children = new HashSet<>();
        this.children.addAll(Arrays.asList(children));
    }

}
