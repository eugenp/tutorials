package com.baeldung.spring.di.serviceImpl;



import com.baeldung.spring.di.service.Employee;

public class EmployeeImp implements Employee {

   @Override
   public void showEmployeeInfo() {
      System.out.println("Inside showEmployeeInfo() method.");
   }

}
