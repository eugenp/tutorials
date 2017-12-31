package com.baeldung.spring.di.serviceImpl;

import com.baeldung.spring.di.service.Department;

public class DepartmentImp implements Department {

   @Override
   public void showDepartmentInfo() {
	   
      System.out.println("Inside showDepartmentInfo() method");
   
   }

}
