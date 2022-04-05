package com.baeldung.findanelement;

import java.util.Iterator;
import java.util.List;

import org.apache.commons.collections4.IterableUtils;

import com.google.common.base.Predicate;
import com.google.common.collect.Iterables;

public class FindACustomerInGivenList {

    public Customer findUsingGivenIndex(int indexOfCustomer, List<Customer> customers) {
        if (indexOfCustomer >= 0 && indexOfCustomer < customers.size())
            return customers.get(indexOfCustomer);
        return null;
    }

    public int findUsingIndexOf(Customer customer, List<Customer> customers) {
        return customers.indexOf(customer);
    }

    public boolean findUsingContains(Customer customer, List<Customer> customers) {
        return customers.contains(customer);
    }

    public Customer findUsingIterator(String name, List<Customer> customers) {
        Iterator<Customer> iterator = customers.iterator();
        while (iterator.hasNext()) {
            Customer customer = iterator.next();
            if (customer.getName().equals(name)) {
                return customer;
            }
        }
        return null;
    }

    public Customer findUsingEnhancedForLoop(String name, List<Customer> customers) {
        for (Customer customer : customers) {
            if (customer.getName().equals(name)) {
                return customer;
            }
        }
        return null;
    }

    public Customer findUsingStream(String name, List<Customer> customers) {
        return customers.stream()
            .filter(customer -> customer.getName().equals(name))
            .findFirst()
            .orElse(null);
    }

    public Customer findUsingParallelStream(String name, List<Customer> customers) {
        return customers.parallelStream()
            .filter(customer -> customer.getName().equals(name))
            .findAny()
            .orElse(null);
    }

    public Customer findUsingGuava(String name, List<Customer> customers) {
        return Iterables.tryFind(customers, new Predicate<Customer>() {
            public boolean apply(Customer customer) {
                return customer.getName().equals(name);
            }
        }).orNull();
    }

    public Customer findUsingApacheCommon(String name, List<Customer> customers) {
        return IterableUtils.find(customers, new org.apache.commons.collections4.Predicate<Customer>() {
            public boolean evaluate(Customer customer) {
                return customer.getName().equals(name);
            }
        });
    }

}