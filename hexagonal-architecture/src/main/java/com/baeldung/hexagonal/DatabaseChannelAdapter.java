package com.baeldung.hexagonal;

public class DatabaseChannelAdapter implements IEmployee<EmployeeDomainObject, EmployeeCommand> {
    public void save(EmployeeDomainObject t, EmployeeCommand commandObject) {
        String underLyingJPAEntity = commandObject.getEntityClass();
        System.out.println("call save on " + underLyingJPAEntity);
    }

    public void delete(EmployeeDomainObject t, EmployeeCommand commandObject) {
        String underLyingJPAEntity = commandObject.getEntityClass();
        System.out.println("call delete on " + underLyingJPAEntity);
    }

}
