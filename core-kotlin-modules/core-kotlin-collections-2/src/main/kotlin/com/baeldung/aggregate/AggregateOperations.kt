package com.baeldung.aggregate

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
        return numbers.fold(100) { total, it ->
            println("total = $total, it = $it")
            total - it
        } // ((((100 - 1)-15)-3)-8) = 73
    }

    fun foldRightList(): Int {
        return numbers.foldRight(100) { it, total ->
            println("total = $total, it = $it")
            total - it
        }  // ((((100-8)-3)-15)-1) = 73
    }

    fun foldIndexedList(): Int {
        return numbers.foldIndexed(100) { index, total, it ->
            println("total = $total, it = $it, index = $index")
            if (index.minus(2) >= 0) total - it else total
        } // ((100 - 3)-8) = 89
    }

    fun foldRightIndexedList(): Int {
        return numbers.foldRightIndexed(100) { index, it, total ->
            println("total = $total, it = $it, index = $index")
            if (index.minus(2) >= 0) total - it else total
        } // ((100 - 8)-3) = 89
    }

    fun reduceList(): Int {
        return numbers.reduce { total, it ->
            println("total = $total, it = $it")
            total - it
        } // (((1 - 15)-3)-8) = -25
    }

    fun reduceRightList(): Int {
        return numbers.reduceRight() { it, total ->
            println("total = $total, it = $it")
            total - it
        } // ((8-3)-15)-1) = -11
    }

    fun reduceIndexedList(): Int {
        return numbers.reduceIndexed { index, total, it ->
            println("total = $total, it = $it, index = $index")
            if (index.minus(2) >= 0) total - it else total
        } // ((1-3)-8) = -10
    }

    fun reduceRightIndexedList(): Int {
        return numbers.reduceRightIndexed { index, it, total ->
            println("total = $total, it = $it, index = $index")
            if (index.minus(2) >= 0) total - it else total
        } // ((8-3) = 5
    }
}
