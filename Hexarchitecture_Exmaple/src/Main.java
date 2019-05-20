public class Main {
    public static void main(String[] args) {
        EmployeeDomainDao<EmployeeDomainObject, EmployeeCommand> dao = new EmployeeDomainDao<EmployeeDomainObject, EmployeeCommand>();
        IPersists<EmployeeDomainObject, EmployeeCommand> adapter = new DataBaseChannelAdapter();
        dao.setAdapter(adapter);
        EmployeeDomainObject emp = new EmployeeDomainObject();
        emp.setName("Shamik Mitra");
        emp.setAddress("India,Kolkata");
        EmployeeCommand command = new EmployeeCommand();
        command.setEntityClass("com.employeemanagement.entity.EmployeeJpaEntity");
        dao.save(emp, command);
        dao.delete(emp, command);
    }
}