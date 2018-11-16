package com.baeldung.dataclass

fun main(args: Array<String>) {

    val movie = Movie("Whiplash", "Sony Pictures", 8.5F)

    println(movie.name) //Whiplash
    println(movie.studio) //Sony Pictures
    println(movie.rating) //8.5

    movie.rating = 9F

    println(movie.toString()) //Movie(name=Whiplash, studio=Sony Pictures, rating=9.0)

    val betterRating = movie.copy(rating = 9.5F)
    println(betterRating.toString()) //Movie(name=Whiplash, studio=Sony Pictures, rating=9.5)

    movie.component1() //name
    movie.component2() //studio
    movie.component3() //rating

    val(name, studio, rating) = movie

    fun getMovieInfo() = movie
    val(namef, studiof, ratingf) = getMovieInfo()
}