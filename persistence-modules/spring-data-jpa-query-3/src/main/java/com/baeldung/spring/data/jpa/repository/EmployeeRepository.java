package com.baeldung.spring.data.jpa.repository;

import com.baeldung.spring.data.jpa.entity.Employee;
import java.util.List;
import net.bytebuddy.TypeCache.Sort;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;

public interface EmployeeRepository extends JpaRepository<Employee, Integer> {

  @Query(value = "SELECT e FROM Employee e")
  List<Employee> findAllEmployees(Sort sort);

  @Query("SELECT e FROM Employee e WHERE e.employeeSalary = ?1")
  Employee findAllEmployeesWithSalary(Long salary);

  @Query("SELECT e FROM Employee e WHERE e.employeeName = ?1 and e.employeeSalary = ?2")
  Employee findUserByNameAndSalary(String name, Long salary);

  @Query(
    value = "SELECT * FROM Employee e WHERE e.employeeSalary = ?1",
    nativeQuery = true)
  Employee findUserBySalaryNative(Long salary);

  @Query("SELECT e FROM Employee e WHERE e.employeeName = :name and e.employeeSalary = :salary")
  Employee findUserByEmployeeNameAndSalaryNamedParameters(
    @Param("name") String employeeName,
    @Param("salary") Long employeeSalary);

  @Query(value = "SELECT * FROM Employee e WHERE e.employeeName = :name and e.employeeSalary = :salary",
    nativeQuery = true)
  Employee findUserByNameAndSalaryNamedParamsNative(
    @Param("name") String employeeName,
    @Param("salary") Long employeeSalary);

}
