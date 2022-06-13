package com.baeldung.collections.sorting;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertTrue;

import java.util.ArrayList;
import java.util.Collections;
import java.util.Comparator;
import java.util.Date;
import java.util.List;
import org.apache.commons.lang.time.DateUtils;
import org.junit.Before;
import org.junit.Test;

public class EmployeeSortingByDateUnitTest {

  private List<Employee> employees = new ArrayList<>();
  private List<Employee> employeesSortedByDateAsc = new ArrayList<>();
  private List<Employee> employeesSortedByDateDesc = new ArrayList<>();

  @Before
  public void initVariables() {

    Collections.addAll(employees,
      new Employee("Earl", 43, 10000, DateUtils.addMonths(new Date(), -2)),
      new Employee("Frank", 33, 7000, DateUtils.addDays(new Date(), -20)),
      new Employee("Steve", 26, 6000, DateUtils.addDays(new Date(), -10)),
      new Employee("Jessica", 23, 4000, DateUtils.addMonths(new Date(), -6)),
      new Employee("Pearl", 33, 6000, DateUtils.addYears(new Date(), -1)),
      new Employee("John", 23, 5000, new Date())
    );

    Collections.addAll(employeesSortedByDateDesc,
      new Employee("John", 23, 5000, new Date()),
      new Employee("Steve", 26, 6000, DateUtils.addDays(new Date(), -10)),
      new Employee("Frank", 33, 7000, DateUtils.addDays(new Date(), -20)),
      new Employee("Earl", 43, 10000, DateUtils.addMonths(new Date(), -2)),
      new Employee("Jessica", 23, 4000, DateUtils.addMonths(new Date(), -6)),
      new Employee("Pearl", 33, 6000, DateUtils.addYears(new Date(), -1))
    );

    Collections.addAll(employeesSortedByDateAsc,
      new Employee("Pearl", 33, 6000, DateUtils.addYears(new Date(), -1)),
      new Employee("Jessica", 23, 4000, DateUtils.addMonths(new Date(), -6)),
      new Employee("Earl", 43, 10000, DateUtils.addMonths(new Date(), -2)),
      new Employee("Frank", 33, 7000, DateUtils.addDays(new Date(), -20)),
      new Employee("Steve", 26, 6000, DateUtils.addDays(new Date(), -10)),
      new Employee("John", 23, 5000, new Date())
    );
  }

  @Test
  public void givenEmpList_SortEmpList_thenSortedListinNaturalOrder() {
    Collections.sort(employees);

    assertEquals(employees, employeesSortedByDateAsc);
  }

  @Test
  public void givenEmpList_SortEmpList_thenCheckSortedList() {

    Collections.sort(employees, new Comparator<Employee>() {
      public int compare(Employee o1, Employee o2) {
        return o1.getJoiningDate().compareTo(o2.getJoiningDate());
      }
    });

    assertEquals(employees, employeesSortedByDateAsc);
  }

  @Test
  public void givenEmpList_SortEmpList_thenCheckSortedListV1() {

    Collections.sort(employees, new Comparator<Employee>() {
      public int compare(Employee emp1, Employee emp2) {
        if (emp1.getJoiningDate() == null || emp2.getJoiningDate() == null)
          return 0;
        return emp1.getJoiningDate().compareTo(emp2.getJoiningDate());
      }
    });

    assertEquals(employees, employeesSortedByDateAsc);
  }

  @Test
  public void givenEmpList_SortEmpList_thenSortedListinAscOrder() {
    Collections.sort(employees, Collections.reverseOrder());

    assertEquals(employees, employeesSortedByDateDesc);
  }

  @Test
  public void givenEmpList_SortEmpList_thenCheckSortedListAsc() {

    Collections.sort(employees, new Comparator<Employee>() {
      public int compare(Employee emp1, Employee emp2) {
        return emp2.getJoiningDate().compareTo(emp1.getJoiningDate());
      }
    });

    assertEquals(employees, employeesSortedByDateDesc);
  }

  @Test
  public void givenEmpList_SortEmpList_thenCheckSortedListAscV1() {

    Collections.sort(employees, new Comparator<Employee>() {
      public int compare(Employee emp1, Employee emp2) {
        if (emp1.getJoiningDate() == null || emp2.getJoiningDate() == null)
          return 0;
        return emp2.getJoiningDate().compareTo(emp1.getJoiningDate());
      }
    });

    assertEquals(employees, employeesSortedByDateDesc);
  }

  @Test
  public void givenEmpList_SortEmpList_thenCheckSortedListDescLambda() {

    Collections.sort(employees,
      (emp1, emp2) -> emp2.getJoiningDate().compareTo(emp1.getJoiningDate()));

    assertEquals(employees, employeesSortedByDateDesc);
  }

  @Test
  public void givenEmpList_SortEmpList_thenCheckSortedListDescLambdaV1() {

    Collections.sort(employees, (emp1, emp2) -> {
      if (emp1.getJoiningDate() == null || emp2.getJoiningDate() == null)
        return 0;
      return emp2.getJoiningDate().compareTo(emp1.getJoiningDate());
    });

    assertEquals(employees, employeesSortedByDateDesc);
  }

  @Test
  public void givenEmpList_SortEmpList_thenCheckSortedListAscLambda() {
    Collections.sort(employees,
      Comparator.comparing(Employee::getJoiningDate));
    assertEquals(employees, employeesSortedByDateAsc);
  }

}
