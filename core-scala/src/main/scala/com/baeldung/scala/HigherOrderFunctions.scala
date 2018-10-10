

package com.baeldung.scala

object HigherOrderFunctions {

    def mapReduce(r: (Int, Int) => Int, i: Int, m: Int => Int, a: Int, b: Int) = {
        def iter(a: Int, result: Int): Int = {
            if (a > b) result
            else iter(a + 1, r(m(a), result))
        }
        iter(a, i)
    }
}