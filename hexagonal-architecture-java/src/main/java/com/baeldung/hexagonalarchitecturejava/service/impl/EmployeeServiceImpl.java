package com.baeldung.hexagonalarchitecturejava.service.impl;

import com.baeldung.hexagonalarchitecturejava.domain.Employee;
import com.baeldung.hexagonalarchitecturejava.repository.EmployeeRepository;
import com.baeldung.hexagonalarchitecturejava.service.IEmployeeService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.List;

@Service
public class EmployeeServiceImpl implements IEmployeeService
{
    @Autowired
    private EmployeeRepository repository;

    @Override
    public List<Employee> getAllEmployee() {
        List<Employee> employees = repository.findAll();
        return employees;
    }

    @Override
    public Employee save(Employee employee)
    {
        Employee emp=null;
        if(null != employee)
        {
            emp = repository.save(employee);
        }
        return emp;
    }

    @Override
    public Employee update(Employee employee) throws Exception {
        Employee existingEmployeeData = findEmployeeById(employee.getId());

        if(null!=existingEmployeeData)
        {
            updateNewEmployeeData(existingEmployeeData,employee);
            existingEmployeeData = save(existingEmployeeData);
            return existingEmployeeData;
        }
        else
        {
            throw new Exception("Record not found for given employee ID");
        }
    }

    @Override
    public Employee findEmployeeById(Long employeeId)
    {
        return repository.findById(employeeId).orElse(null);
    }

    @Override
    public void deleteById(Long employeeId)
    {
        repository.deleteById(employeeId);
    }

    private void updateNewEmployeeData(Employee existingEmployeeData,Employee employee)
    {
        if(null!=employee.getName())
        {
            existingEmployeeData.setName(employee.getName());
        }
        if(null!=employee.getSalary())
        {
            existingEmployeeData.setSalary(employee.getSalary());
        }
    }
}
