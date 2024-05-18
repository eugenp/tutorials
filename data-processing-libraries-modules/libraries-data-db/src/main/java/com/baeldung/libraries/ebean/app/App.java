package com.baeldung.libraries.ebean.app;

import java.util.Arrays;

import com.baeldung.libraries.ebean.model.Address;
import com.baeldung.libraries.ebean.model.Customer;

import io.ebean.DB;
import io.ebean.Database;
import io.ebean.annotation.Transactional;

public class App {

    public static void main(String[] args) {
        insertAndDeleteInsideTransaction();
        crudOperations();
        queryCustomers();
    }

    @Transactional
    public static void insertAndDeleteInsideTransaction() {

        Customer c1 = getCustomer();
        Database server = DB.getDefault();
        server.save(c1);
        Customer foundC1 = server.find(Customer.class, c1.getId());
        server.delete(foundC1);
    }

    public static void crudOperations() {
    	
        Address a1 = new Address("5, Wide Street", null, "New York");
        Customer c1 = new Customer("John Wide", a1);

        Database server = DB.getDefault();
        server.save(c1);

        c1.setName("Jane Wide");
        c1.setAddress(null);
        server.save(c1);

        Customer foundC1 = DB.find(Customer.class, c1.getId());

        DB.delete(foundC1);
    }

    public static void queryCustomers() {
        Address a1 = new Address("1, Big Street", null, "New York");
        Customer c1 = new Customer("Big John", a1);

        Address a2 = new Address("2, Big Street", null, "New York");
        Customer c2 = new Customer("Big John", a2);

        Address a3 = new Address("3, Big Street", null, "San Jose");
        Customer c3 = new Customer("Big Bob", a3);

        DB.saveAll(Arrays.asList(c1, c2, c3));

        Customer customer = DB.find(Customer.class)
            .select("name")
            .fetch("address", "city")
            .where()
            .eq("city", "San Jose")
            .findOne();

        DB.deleteAll(Arrays.asList(c1, c2, c3));
    }

    private static Customer getCustomer() {
        Address a1 = new Address("1, Big Street", null, "New York");
        Customer c1 = new Customer("Big John", a1);
        return c1;
    }

}
