package com.baeldung.threadpool;

import java.util.Set;

import com.google.common.collect.Sets;

public class TreeNode {

    int value;

    Set<TreeNode> children;

    public TreeNode(int value, TreeNode... children) {
        this.value = value;
        this.children = Sets.newHashSet(children);
    }

}
