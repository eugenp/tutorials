package com.baeldung.datastructures

/**
 * Example of how to use the {@link Node} class.
 *
 */
fun main(args: Array<String>) {
    val tree = Node(4)
    val keys = arrayOf(1, 2, 5, 10, 2, 3, 4, 6, 11)
    for (key in keys) {
        tree.insert(key)
    }
    val node = tree.find(4)!!
    print("Node with value ${node.key} [left = ${node.left?.key}, right = ${node.right?.key}]")
    node.delete(3)
    print(tree.visit().joinToString { it.toString() })
}
