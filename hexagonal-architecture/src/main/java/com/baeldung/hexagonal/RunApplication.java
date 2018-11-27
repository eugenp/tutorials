package com.baeldung.hexagonal;

public class RunApplication {
    public static void main(String[] args) {
        EmployeeDomainDao<EmployeeDomainObject, EmployeeCommand> dao = new EmployeeDomainDao<EmployeeDomainObject, EmployeeCommand>();
        IEmployee<EmployeeDomainObject, EmployeeCommand> adapter = new DatabaseChannelAdapter();
        dao.setAdapter(adapter);
        EmployeeDomainObject emp = new EmployeeDomainObject();
        emp.setName("John Doe");
        emp.setAddress("New York,USA");
        EmployeeCommand command = new EmployeeCommand();
        command.setEntityClass("com.baeldung.entity.EmployeeJpaEntity");
        System.out.println(emp);
        dao.save(emp, command);
        dao.delete(emp, command);
    }

}
