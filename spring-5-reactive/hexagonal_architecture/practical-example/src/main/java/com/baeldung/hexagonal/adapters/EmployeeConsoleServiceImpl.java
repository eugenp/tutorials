package com.baeldung.hexagonal.adapters;

import java.util.Optional;
import java.util.Scanner;

import org.slf4j.Logger;

import com.baeldung.hexagonal.domain.Employee;
import com.baeldung.hexagonal.domain.EmployeeConsoleService;
import com.baeldung.hexagonal.domain.EmployeeDetails;
import com.baeldung.hexagonal.domain.EmployeeId;
import com.baeldung.hexagonal.domain.EmployeeService;
/**
 * 
 * Primary Adapter.
 *
 */
public class EmployeeConsoleServiceImpl implements EmployeeConsoleService {
	private final Logger logger;
	/**
	   * Constructor
	   */
	  public EmployeeConsoleServiceImpl(Logger logger) {
	    this.logger = logger;
	  }
	  
	  @Override
	  public void createEmployee(EmployeeService service, Scanner scanner) {
		try {
	    logger.info( "What is your first name?" );
	    String firstName = readString( scanner );
	    logger.info( "What is your last name?" );
	    String lastName = readString( scanner );
	    EmployeeDetails details = new EmployeeDetails(firstName, lastName);
	      Employee employee = new Employee( new EmployeeId(), details);
	      Optional<EmployeeId> id = service.insertEmployee( employee );
	      
	      if (id.isPresent()) {
	        logger.info( "Submitted employee entry with id: {}", id.get() );
	      } else {
	        logger.info( "Failed submitting employee entry - please try again." );
	      }
	    } catch (Exception e) {
	    	logger.info( "Failed submitting employee entry - please try again." );
	    	e.printStackTrace();
	      
	    }
	  }

	  
	 private String readString(Scanner scanner) {
		    System.out.print( "> " );
		    return scanner.next();
		  }
	@Override
	public void retrieveEmployee(EmployeeService service, Scanner scanner) {
		logger.info( "What is your employee id?" );
	    String inemployeeId = readString( scanner );
	    EmployeeId employeeId = new EmployeeId(Integer.parseInt(inemployeeId));
	    Optional<Employee> employee = service.retrieveEmployee( employeeId );
	    if(employee.isPresent())
	    logger.info( "Retrieved employee firstname {}", employee.get().getEmployeeDetails().getFirstName() );
	    else 
	    logger.info( "No Matching Records found");	
	}
}

