package com.baeldung.destructuringdeclarations

fun main(args: Array<String>) {

        //2.1. Objects
    val person = Person(1, "Jon Snow", 20)
    val(id, name, age) = person

    println(id)     //1
    println(name)   //Jon Snow
    println(age)    //20

    //2.2. Functions
    fun getPersonInfo() = Person(2, "Ned Stark", 45)
    val(idf, namef, agef) = getPersonInfo()

    fun twoValuesReturn(): Pair<Int, String> {

        // needed code

        return Pair(1, "success")
    }

    // Now, to use this function:
    val (result, status) = twoValuesReturn()

    //2.3. Collections and For-loops
    var map: HashMap<Int, Person> = HashMap()
    map.put(1, person)

    for((key, value) in map){
        println("Key: $key, Value: $value")
    }

    //2.4. Underscore and Destructuring in Lambdas
    val (_, name2, age2) = person
    val (id3, name3) = person

    map.mapValues { entry -> "${entry.value}!" }
    map.mapValues { (key, value) -> "$value!" }

}