package edu.baeldung.hexagonalarchitecture.mock;

import edu.baeldung.hexagonalarchitecture.core.App;
import edu.baeldung.hexagonalarchitecture.pojo.Customer;

import java.util.ArrayList;
import java.util.List;
import java.util.Optional;

public class AppMock implements App {

    @Override
    public Customer createCustomer(Customer customer) {
        Customer predictable = new Customer();
        predictable.setFullName("This is predictable");
        predictable.setIdentification("Pred");
        return predictable;
    }

    @Override
    public List<Customer> findAll() {
        List<Customer> testCustomers = new ArrayList<>();

        Customer predictable = new Customer();
        predictable.setFullName("This is predictable as list");
        predictable.setIdentification("PredList");

        testCustomers.add(predictable);
        return testCustomers;
    }

    @Override
    public Optional<Customer> findCustomerById(String customerId) {
        Customer predictable = new Customer();
        predictable.setFullName("This is predictable and optional");
        predictable.setIdentification("PredOp");
        return Optional.of(predictable);
    }
}
