package com.baeldung.libraries.ebean.app;

import com.baeldung.libraries.ebean.model.Address;
import com.baeldung.libraries.ebean.model.Customer;
import io.ebean.Ebean;
import io.ebean.EbeanServer;
import io.ebean.annotation.Transactional;

import java.util.Arrays;

public class App {

    public static void main(String[] args) {
        insertAndDeleteInsideTransaction();
        crudOperations();
        queryCustomers();
    }

    @Transactional
    public static void insertAndDeleteInsideTransaction() {

        Customer c1 = getCustomer();
        EbeanServer server = Ebean.getDefaultServer();
        server.save(c1);
        Customer foundC1 = server.find(Customer.class, c1.getId());
        server.delete(foundC1);
    }

    public static void crudOperations() {
    	
        Address a1 = new Address("5, Wide Street", null, "New York");
        Customer c1 = new Customer("John Wide", a1);

        EbeanServer server = Ebean.getDefaultServer();
        server.save(c1);

        c1.setName("Jane Wide");
        c1.setAddress(null);
        server.save(c1);

        Customer foundC1 = Ebean.find(Customer.class, c1.getId());

        Ebean.delete(foundC1);
    }

    public static void queryCustomers() {
        Address a1 = new Address("1, Big Street", null, "New York");
        Customer c1 = new Customer("Big John", a1);

        Address a2 = new Address("2, Big Street", null, "New York");
        Customer c2 = new Customer("Big John", a2);

        Address a3 = new Address("3, Big Street", null, "San Jose");
        Customer c3 = new Customer("Big Bob", a3);

        Ebean.saveAll(Arrays.asList(c1, c2, c3));

        Customer customer = Ebean.find(Customer.class)
            .select("name")
            .fetch("address", "city")
            .where()
            .eq("city", "San Jose")
            .findOne();

        Ebean.deleteAll(Arrays.asList(c1, c2, c3));
    }

    private static Customer getCustomer() {
        Address a1 = new Address("1, Big Street", null, "New York");
        Customer c1 = new Customer("Big John", a1);
        return c1;
    }

}
