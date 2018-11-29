package com.baeldung.operators

interface Page<T> {
    fun pageNumber(): Int
    fun pageSize(): Int
    fun elements(): MutableList<T>
}

operator fun <T> Page<T>.get(index: Int): T = elements()[index]
operator fun <T> Page<T>.get(start: Int, endExclusive: Int): List<T> = elements().subList(start, endExclusive)
operator fun <T> Page<T>.set(index: Int, value: T) {
    elements()[index] = value
}

operator fun <T> Page<T>.contains(element: T): Boolean = element in elements()
operator fun <T> Page<T>.iterator() = elements().iterator()