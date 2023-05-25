package com.baeldung.algorithms.balancedbinarytree;

import java.util.Arrays;
import java.util.Collection;

class BinaryTreeDataProvider {

    static Collection<Tree> balancedTrees() {
        return Arrays.asList(
            null,
            leaf(1),
            tree(1, leaf(2), leaf(3)),
            tree(
                1,
                leaf(2),
                tree(3, leaf(4), null)
            ),
            tree(
                1,
                tree(
                    2,
                    tree(3, leaf(4), null),
                    leaf(5)
                ),
                tree(
                    6,
                    leaf(7),
                    tree(8, null, leaf(9))
                )
            )
        );
    }

    static Collection<Tree> unbalancedTrees() {
        return Arrays.asList(
            tree(
                1,
                tree(2, leaf(3), null),
                null
            ),
            tree(
                1,
                tree(
                    2,
                    tree(3, leaf(4), leaf(5)),
                    null
                ),
                tree(6, leaf(7), null)
            ),
            tree(
                1,
                tree(2, leaf(3), null),
                tree(
                    4,
                    tree(5, leaf(6), leaf(7)),
                    null
                )
            )
        );
    }

    private static Tree leaf(int value) {
        return new Tree(value, null, null);
    }

    private static Tree tree(int value, Tree left, Tree right) {
        return new Tree(value, left, right);
    }
}
