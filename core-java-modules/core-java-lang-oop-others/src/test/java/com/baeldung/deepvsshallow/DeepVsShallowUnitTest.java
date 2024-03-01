package com.baeldung.deepvsshallow;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertNotEquals;

import org.junit.jupiter.api.Test;

public class DeepVsShallowUnitTest {

  @Test
  public void whenDepartmentIsCloned_thenEmployeeIsNotCloned() throws CloneNotSupportedException {
    ITDepartment ITDepartment = new ITDepartment("IT");
    ITDepartment.setEmployee(new Employee("John"));
    ITDepartment ITDepartmentClone = ITDepartment.clone();
    ITDepartmentClone.setName("Marketing");
    ITDepartmentClone.getEmployee()
      .setName("Mary");

    assertNotEquals(ITDepartment.getName(), ITDepartmentClone.getName());
    assertEquals(ITDepartment.getEmployee()
      .getName(), ITDepartmentClone.getEmployee()
      .getName());
  }

  @Test
  public void whenDepartmentIsCloned_thenEmployeeIsAlsoCloned() throws CloneNotSupportedException {
    MarketingDepartment marketingDepartment = new MarketingDepartment("Marketing");
    marketingDepartment.setEmployee(new Employee("John"));
    MarketingDepartment marketingDepartmentClone = marketingDepartment.clone();
    marketingDepartmentClone.setName("IT");
    marketingDepartmentClone.getEmployee()
      .setName("Mary");

    assertNotEquals(marketingDepartment.getName(), marketingDepartmentClone.getName());
    assertNotEquals(marketingDepartment.getEmployee()
      .getName(), marketingDepartmentClone.getEmployee()
      .getName());
  }

}
