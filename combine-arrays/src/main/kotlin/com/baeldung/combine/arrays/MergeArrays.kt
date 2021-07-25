package com.baeldung.combine.arrays

class MergeArrays {
    fun <T> combinePlus(arr1: Array<T>, arr2: Array<T>): Array<T> {
        return arr1 + arr2
    }

    fun <T> combinePlusOperator(arr1: Array<T>, arr2: Array<T>): Array<T> {
        return arr1 + arr2
    }

    inline fun <reified T> combineSpread(arr1: Array<T>, arr2: Array<T>): Array<T> {
        return arrayOf(*arr1, *arr2)
    }

    fun combineCustom(arr1: Array<Int>, arr2: Array<Int>): Array<Int> {
        val mergedArray = Array(arr1.size + arr2.size) { 0 }

        var position = 0
        for (element in arr1) {
            mergedArray[position] = element
            position++
        }

        for (element in arr2) {
            mergedArray[position] = element
            position++
        }

        return mergedArray
    }
}


