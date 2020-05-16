package com.baeldung.operators

data class Point(val x: Int, val y: Int)

operator fun Point.unaryMinus() = Point(-x, -y)
operator fun Point.not() = Point(y, x)
operator fun Point.inc() = Point(x + 1, y + 1)
operator fun Point.dec() = Point(x - 1, y - 1)

operator fun Point.plus(other: Point): Point = Point(x + other.x, y + other.y)
operator fun Point.minus(other: Point): Point = Point(x - other.x, y - other.y)
operator fun Point.times(other: Point): Point = Point(x * other.x, y * other.y)
operator fun Point.div(other: Point): Point = Point(x / other.x, y / other.y)
operator fun Point.rem(other: Point): Point = Point(x % other.x, y % other.y)
operator fun Point.times(factor: Int): Point = Point(x * factor, y * factor)
operator fun Int.times(point: Point): Point = Point(point.x * this, point.y * this)

class Shape {
    val points = mutableListOf<Point>()

    operator fun Point.unaryPlus() {
        points.add(this)
    }
}

fun shape(init: Shape.() -> Unit): Shape {
    val shape = Shape()
    shape.init()

    return shape
}