public interface IPersists<T, TCOMMAND> {
    void save(T t, TCOMMAND commandObject);

    void delete(T t, TCOMMAND commandObject);
}