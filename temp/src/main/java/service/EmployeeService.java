package service;

import dao.EmployeeDaoInterface;
import model.Employee;
import org.springframework.stereotype.Service;

@Service
public class EmployeeService {
    private EmployeeDaoInterface dao;

    public EmployeeService(EmployeeDaoInterface employeeDao) {
        dao = employeeDao;
    }

    public Employee search(String id) {
        return dao.get(id);
    }
}
