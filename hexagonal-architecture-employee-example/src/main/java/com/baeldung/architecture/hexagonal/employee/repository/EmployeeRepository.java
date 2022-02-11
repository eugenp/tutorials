/**
 * 
 */
package com.baeldung.architecture.hexagonal.employee.repository;

import com.baeldung.architecture.hexagonal.employee.dto.Employee;

/**
 * Repository, an outbound port.
 */
public interface EmployeeRepository {
	/**
	 * Retrieve the detail of employee with employee id.
	 * @param employeeId
	 * @return
	 */
	public Employee findById(int employeeId);

	/**
	 * Save the employee detail.
	 * @param firstName
	 * @param lastName
	 * @return
	 */
	public Employee addEmployee(String firstName, String lastName);
}
