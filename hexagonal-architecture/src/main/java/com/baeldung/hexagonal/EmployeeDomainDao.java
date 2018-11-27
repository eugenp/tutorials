package com.baeldung.hexagonal;

public class EmployeeDomainDao<T, TCommand> {

    IEmployee<T, TCommand> adapter;

    public void save(T t, TCommand commandObject) {
        adapter.save(t, commandObject);
    }

    public void delete(T t, TCommand commandObject) {
        adapter.delete(t, commandObject);
    }

    public IEmployee<T, TCommand> getAdapter() {
        return adapter;
    }

    public void setAdapter(IEmployee<T, TCommand> adapter) {
        this.adapter = adapter;
    }

}
