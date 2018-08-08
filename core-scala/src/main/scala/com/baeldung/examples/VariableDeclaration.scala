package com.baeldung.examples

object VariableDeclaration extends App {

    val x:Int = 0
    var friend:String = "David"
    lazy val greet = "Hello" + friend
    val data = Array(2, 4, 5)

    printHelloWorld()
    println(sum(10, 20))

    def printHelloWorld():Unit = {
        println("Hello World")
    }

    def sum(x:Int, y:Int) = x + y

    def sayGreeting(to:String, greet:String="Hi", message:String = "How are you?") = println(greet + " " + to + ", " + message)

    sayGreeting(to="David", message="How are you doing?")
    sayGreeting(greet="Hello", to="Maria")

}
