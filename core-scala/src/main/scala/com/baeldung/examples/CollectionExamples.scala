package com.baeldung.examples

object CollectionExamples extends App {

    val aList = List(5, 3, 9, 2) // create a list
    val sortedAscList = aList.sorted // sort ascending
    val sortedDescList = aList.sortWith(_ > _) // sort descending
    val evenList = aList.filter(_%2 == 0) // filter on some condition

    val mySet = Set(10, 20, 5) // create a set
    val changedSet = mySet + 50 // add an element
    println(changedSet)
    val changedSet2 = mySet - 20 // remove an element
    println(changedSet2)

    val capitals = Map("Japan" -> "Tokyo",
        "England" -> "London",
        "India" -> "New Delhi") // create a map
    val capitalOfJapan = capitals("Japan") // get from map
    println(capitalOfJapan)

}
