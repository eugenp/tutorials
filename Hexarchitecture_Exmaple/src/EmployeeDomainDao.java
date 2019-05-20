public class EmployeeDomainDao<T, TCommand> {
    IPersists<T, TCommand> adapter;

    public void save(T t, TCommand commandObject) {
        adapter.save(t, commandObject);
    }

    public void delete(T t, TCommand commandObject) {
        adapter.delete(t, commandObject);
    }

    public IPersists<T, TCommand> getAdapter() {
        return adapter;
    }

    public void setAdapter(IPersists<T, TCommand> adapter) {
        this.adapter = adapter;
    }
}