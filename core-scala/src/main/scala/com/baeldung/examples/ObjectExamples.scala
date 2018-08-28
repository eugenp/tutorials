package com.baeldung.examples

object ObjectExamples extends App {

    import java.time._
    class Customer(val id:Int, val name:String, private var _age:Int) {
        def age = this._age
        def age_= (age:Int) = _age = age
    }
    object Customer {
        def apply(id:Int, name:String, age:Int) = new Customer(id, name, age)

        def apply(id:Int, name:String, dob:LocalDate) = {
            val p = Period.between(dob, LocalDate.now)
            new Customer(id, name, p.getYears)
        }
    }
    val c = Customer(1, "Varun Sharma", LocalDate.parse("1980-02-27"))

    c.age = 20
    println(c.age)

}
