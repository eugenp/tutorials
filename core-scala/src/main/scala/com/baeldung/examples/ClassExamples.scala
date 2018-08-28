package com.baeldung.examples

object ClassExamples extends App {

    import java.time._
    class Customer(val id:Int, val name:String, dob:LocalDate) {

        private var _age = calculateAge(dob)

        def age = _age // getter
        def age_= (age:Int) = _age = age //setter

        private def calculateAge(dob:LocalDate) = Period
                                                .between(dob, LocalDate.now)
                                                .getYears

        //override def toString: String = s"(id:${id}, name:${name}, age:${age})"
    }
    val c = new Customer(1, "Varun Sharma", LocalDate.parse("1980-02-27"))
    println(c)

    case class Address(addressLn1:String, area:String, city:String, zip:String)

    val address=Address("102, Raycon Lotus Apartment", "AECS Layout", "Bangalore", "560037")

    val anotherAddress = address.copy(addressLn1="41/2, ITPL Road")

    println(address)
    println(anotherAddress)

}
