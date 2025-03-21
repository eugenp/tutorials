package com.baeldung.javafeatures

fun main() {
    outer@ for (i in 1..5) {
        for (j in 1..5) {
            if (j == 2) break@outer
            println("$i, $j")
        }
    }
}
