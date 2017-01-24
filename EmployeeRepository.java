package com.baeldung.di.repository;

import com.baeldung.di.employee.*;

/**
 * 
 * @author shaffenbredl
 *
 */

public interface EmployeeRepository {

	Employee getEmployeeById(long empId);

}
