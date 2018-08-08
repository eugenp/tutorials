package com.baeldung.examples

object TupleExamples extends App {

    val empSal:(String, Int) = ("David", 10000)

    def getMaxMinSalary(empSal:Array[(String, Int)]) = {
        val minEmpSal = empSal.minBy(empSal => empSal._2)
        val maxEmpSal = empSal.maxBy(empSal => empSal._2)
        (maxEmpSal._2, minEmpSal._2)
    }
    val empSalArr = Array(("David", 12000), ("Maria", 15000),
            ("Elisa", 11000), ("Adam", 8000))

    val (maxSalary, minSalary) = getMaxMinSalary(empSalArr)
    println("Max Salary: " + maxSalary + " and Min Salary: " + minSalary)

}
