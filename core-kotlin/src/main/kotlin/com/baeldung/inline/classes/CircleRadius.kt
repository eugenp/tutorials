package com.baeldung.inline.classes

interface Drawable {
    fun draw()
}

inline class CircleRadius(private val circleRadius : Double) : Drawable {
    val diameterOfCircle get() = 2 * circleRadius
    fun areaOfCircle() = 3.14 * circleRadius * circleRadius

    override fun draw() {
        println("Draw my circle")
    }
}