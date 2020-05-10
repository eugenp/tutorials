package com.baeldung.kotlin.objects

object ReverseStringComparator : Comparator<String> {
    override fun compare(o1: String, o2: String) = o1.reversed().compareTo(o2.reversed())
}
