package com.baeldung.scala

class Employee(val name: String, var salary: Int, annualIncrement: Int = 20) extends AnyRef {

    def incrementSalary(): Unit = {
        salary += annualIncrement
    }

    override def toString = s"Employee(name=$name, salary=$salary)"
}