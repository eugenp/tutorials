import dao.EmployeeDaoInterface;
import model.Employee;
import org.springframework.stereotype.Component;

import java.util.HashMap;

@Component
public class EmployeeDaoMock implements EmployeeDaoInterface {
    private HashMap<String, Employee> employees = new HashMap<String, Employee>();

    public EmployeeDaoMock() {
        employees.put("mock", new Employee("mockName", "mockID", "mockAddress"));
    }

    public Employee get(String id) {
        return employees.get(id);
    }
}
