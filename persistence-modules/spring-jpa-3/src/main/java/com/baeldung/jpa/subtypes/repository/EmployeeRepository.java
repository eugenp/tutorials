package com.baeldung.jpa.subtypes.repository;

import java.util.List;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;

import com.baeldung.jpa.subtypes.entity.ContractEmployee;
import com.baeldung.jpa.subtypes.entity.Employee;
import com.baeldung.jpa.subtypes.entity.PermanentEmployee;

public interface EmployeeRepository extends JpaRepository<Employee, Long> {

    @Query("SELECT e FROM Employee e WHERE type(e) = 'PERM' AND e.employeeId < :idlimit " 
    + "AND e.name LIKE :prefix% ")
    List<PermanentEmployee> filterPermanent(@Param("idlimit") int idlimit, @Param("prefix") String prefix);

    @Query("SELECT e FROM Employee e WHERE type(e) = 'CNTR' AND e.contractPeriod < :period " 
    + "AND e.name LIKE :prefix%  ")
    List<ContractEmployee> filterContract(@Param("period") int period, @Param("prefix") String prefix);
}
