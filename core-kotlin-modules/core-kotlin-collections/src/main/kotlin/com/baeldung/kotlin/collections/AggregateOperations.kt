package com.baeldung.kotlin.collections

class AggregateOperations {
    private val numbers = listOf(1, 15, 3, 8)

    fun countList(): Int {
        return numbers.count()
    }

    fun sumList(): Int {
        return numbers.sum()
    }

    fun averageList(): Double {
        return numbers.average()
    }

    fun maximumInList(): Int? {
        return numbers.max()
    }

    fun minimumInList(): Int? {
        return numbers.min()
    }

    fun maximumByList(): Int? {
        return numbers.maxBy { it % 5 }
    }

    fun minimumByList(): Int? {
        return numbers.minBy { it % 5 }
    }

    fun maximumWithList(): String? {
        val strings = listOf("Berlin", "Kolkata", "Prague", "Barcelona")
        return strings.maxWith(compareBy { it.length % 4 })
    }

    fun minimumWithList(): String? {
        val strings = listOf("Berlin", "Kolkata", "Prague", "Barcelona")
        return strings.minWith(compareBy { it.length % 4 })
    }

    fun sumByList(): Int {
        return numbers.sumBy { it * 5 }
    }

    fun sumByDoubleList(): Double {
        return numbers.sumByDouble { it.toDouble() / 8 }
    }

    fun foldList(): Int {
        println("fold operation")
        val result = numbers.fold(100) { total, it ->
            println("total = $total, it = $it")
            total - it
        }
        println(result) // ((((100 - 1)-15)-3)-8) = 73
        return result
    }

    fun foldRightList(): Int {
        println("foldRight operation")
        val result = numbers.foldRight(100) { it, total ->
            println("total = $total, it = $it")
            total - it
        }
        println(result) // ((((100-8)-3)-15)-1) = 73
        return result
    }

    fun foldIndexedList(): Int {
        println("foldIndexed operation")
        val result = numbers.foldIndexed(100) { index, total, it ->
            println("total = $total, it = $it, index = $index")
            if (index.minus(2) >= 0) total - it else total
        }
        println(result) // ((100 - 3)-8) = 89
        return result
    }

    fun foldRightIndexedList(): Int {
        println("foldRightIndexed operation")
        val result = numbers.foldRightIndexed(100) { index, it, total ->
            println("total = $total, it = $it, index = $index")
            if (index.minus(2) >= 0) total - it else total
        }
        println(result) // ((100 - 8)-3) = 89
        return result
    }

    fun reduceList(): Int {
        println("reduce operation")
        val result = numbers.reduce { total, it ->
            println("total = $total, it = $it")
            total - it
        }
        println(result) // (((1 - 15)-3)-8) = -25
        return result
    }

    fun reduceRightList(): Int {
        println("reduceRight operation")
        val result = numbers.reduceRight() { it, total ->
            println("total = $total, it = $it")
            total - it
        }
        println(result) // ((8-3)-15)-1) = -11
        return result
    }

    fun reduceIndexedList(): Int {
        println("reduceIndexed operation")
        val result = numbers.reduceIndexed { index, total, it ->
            println("total = $total, it = $it, index = $index")
            if (index.minus(2) >= 0) total - it else total
        }
        println(result) // ((1-3)-8) = -10
        return result
    }

    fun reduceRightIndexedList(): Int {
        println("reduceRightIndexed operation")
        val result = numbers.reduceRightIndexed { index, it, total ->
            println("total = $total, it = $it, index = $index")
            if (index.minus(2) >= 0) total - it else total
        }
        println(result) // ((8-3) = 5
        return result
    }
}
