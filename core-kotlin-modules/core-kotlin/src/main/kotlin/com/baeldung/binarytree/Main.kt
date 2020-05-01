package com.baeldung.binarytree

/**
 * Example of how to use the {@link Node} class.
 *
 */
fun main(args: Array<String>) {
    val tree = Node(4)
    val keys = arrayOf(8, 15, 21, 3, 7, 2, 5, 10, 2, 3, 4, 6, 11)
    for (key in keys) {
        tree.insert(key)
    }
    val node = tree.find(4)!!
    println("Node with value ${node.key} [left = ${node.left?.key}, right = ${node.right?.key}]")
    println("Delete node with key = 3")
    node.delete(3)
    print("Tree content after the node elimination: ")
    println(tree.visit().joinToString { it.toString() })
}
