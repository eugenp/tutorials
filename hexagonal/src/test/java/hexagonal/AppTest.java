package hexagonal;

import org.junit.Assert;
import org.junit.Test;

import com.baeldung.domain.EmployeeService;
import com.baeldung.model.Employee;
import com.baeldung.repo.DatabaseEmployeeRepository;

public class AppTest {
	
	@Test
	public void whenSaveEmployeeInDatabaseThenSuccessfull(){
		Employee e1 = new Employee();
		e1.setName("Test Employee");
		e1.setAddress("Test Employee Address");
		DatabaseEmployeeRepository repo =DatabaseEmployeeRepository.getInstance();
		EmployeeService empService = new EmployeeService(repo);
		empService.saveEmployee(e1);
		
		Employee savedEmployee = repo.getEmployeeById(e1.getId());
		Assert.assertEquals("Name of Both employee object are same", e1.getName(), savedEmployee.getName());
	}

}
