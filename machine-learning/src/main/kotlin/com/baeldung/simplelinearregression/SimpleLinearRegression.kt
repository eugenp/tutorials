package com.baeldung.simplelinearregression

import kotlin.math.pow

class SimpleLinearRegression(private val xs: List<Int>, private val ys: List<Int>) {
    var slope: Double = 0.0
    var yIntercept: Double = 0.0

    init {
        val covariance = calculateCovariance(xs, ys)
        val variance = calculateVariance(xs)
        slope = calculateSlope(covariance, variance)
        yIntercept = calculateYIntercept(ys, slope, xs)
    }

    fun predict(independentVariable: Double) = slope * independentVariable + yIntercept

    fun calculateRSquared(): Double {
        val sst = ys.sumByDouble { y -> (y - ys.average()).pow(2) }
        val ssr = xs.zip(ys) { x, y -> (y - predict(x.toDouble())).pow(2) }.sum()
        return (sst - ssr) / sst
    }

    private fun calculateYIntercept(ys: List<Int>, slope: Double, xs: List<Int>) = ys.average() - slope * xs.average()

    private fun calculateSlope(covariance: Double, variance: Double) = covariance / variance

    private fun calculateCovariance(xs: List<Int>, ys: List<Int>) = xs.zip(ys) { x, y -> (x - xs.average()) * (y - ys.average()) }.sum()

    private fun calculateVariance(xs: List<Int>) = xs.sumByDouble { x -> (x - xs.average()).pow(2) }
}