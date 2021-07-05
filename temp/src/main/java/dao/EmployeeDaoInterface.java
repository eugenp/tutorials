package dao;

import model.Employee;
import org.springframework.stereotype.Component;

@Component
public interface EmployeeDaoInterface {
    public Employee get(String id);
}