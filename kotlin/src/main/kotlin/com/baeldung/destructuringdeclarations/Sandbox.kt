package com.baeldung.destructuringdeclarations

import com.baeldung.destructuringdeclarations.Person
import com.baeldung.destructuringdeclarations.Result

fun main(args: Array<String>) {

    //2.1. Objects
    val person = Person(1, "Jon Snow", 20)
    val(id, name, age) = person

    println(id)     //1
    println(name)   //Jon Snow
    println(age)    //20

    //The equivalent of line 10
/*  val id = person.component1();
    val name = person.component2();
    val age = person.component3();*/

    //2.2. Functions
    fun getPersonInfo() = Person(2, "Ned Stark", 45)
    val(idf, namef, agef) = getPersonInfo()

    fun twoValuesReturn(): Result {

        // needed code

        return Result(1, "success")
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
    val (_, status2) = twoValuesReturn()

    map.mapValues { entry -> "${entry.value}!" }
    map.mapValues { (key, value) -> "$value!" }

    //A pair of parameters vs. a destructuring pair
/*  { a -> ... } // one parameter
    { a, b -> ... } // two parameters
    { (a, b) -> ... } // a destructured pair
    { (a, b), c -> ... } // a destructured pair and another parameter*/

}