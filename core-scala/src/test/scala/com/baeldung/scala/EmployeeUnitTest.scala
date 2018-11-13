package com.baeldung.scala

import org.junit.Assert.assertEquals
import org.junit.Test

class EmployeeUnitTest {

  @Test
  def whenEmployeeSalaryIncremented_thenCorrectSalary() = {
    val employee = new Employee("John Doe", 1000)
    employee.incrementSalary()
    assertEquals(1020, employee.salary)
  }

  @Test
  def givenEmployee_whenToStringCalled_thenCorrectStringReturned() = {
    val employee = new Employee("John Doe", 1000)
    assertEquals("Employee(name=John Doe, salary=1000)", employee.toString)
  }

  @Test
  def givenEmployeeWithTrait_whenToStringCalled_thenCorrectStringReturned() = {
    val employee =
      new Employee("John Doe", 1000) with UpperCasePrinter
    assertEquals("EMPLOYEE(NAME=JOHN DOE, SALARY=1000)", employee.toString)
  }

}


