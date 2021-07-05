package app;

import model.Employee;

public interface ApiInterface {
    Employee get(String id);
}