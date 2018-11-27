package com.baeldung.hexagonal;

public interface IEmployee<T, TCommand> {
    public void save(T t, TCommand com);

    public void delete(T t, TCommand com);
}
