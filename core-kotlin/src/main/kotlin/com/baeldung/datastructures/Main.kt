package com.baeldung.datastructures

fun main(args: Array<String>) {
    print(Node(2, Node(1, Node(0), Node(3)), Node(4)).visit().joinToString { it.toString() })
}
