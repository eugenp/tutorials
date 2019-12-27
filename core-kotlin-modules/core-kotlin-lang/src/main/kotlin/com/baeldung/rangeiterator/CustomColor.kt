package com.baeldung.rangeiterator

import java.lang.IllegalStateException

class CustomColor(val rgb: Int): Comparable<CustomColor> {

    override fun compareTo(other: CustomColor): Int {
        return this.rgb.compareTo(other.rgb)
    }

    operator fun rangeTo(that: CustomColor) = ColorRange(this, that)

    operator fun inc(): CustomColor {
        return CustomColor(rgb + 1)
    }

    init {
        if(rgb < 0x000000 || rgb > 0xFFFFFF){
            throw IllegalStateException("RGB must be between 0 and 16777215")
        }
    }

    override fun toString(): String {
        return "CustomColor(rgb=$rgb)"
    }
}
class ColorRange(override val start: CustomColor,
                 override val endInclusive: CustomColor) : ClosedRange<CustomColor>, Iterable<CustomColor>{

    override fun iterator(): Iterator<CustomColor> {
        return ColorIterator(start, endInclusive)
    }
}

class ColorIterator(val start: CustomColor, val endInclusive: CustomColor) : Iterator<CustomColor> {

    var initValue = start

    override fun hasNext(): Boolean {
        return initValue <= endInclusive
    }

    override fun next(): CustomColor {
        return initValue++
    }
}

fun main(args: Array<String>) {
    val a = CustomColor(0xABCDEF)
    val b = CustomColor(-1)
    val c = CustomColor(0xABCDFF)

    for(color in a..c){
        println(color)
    }
}