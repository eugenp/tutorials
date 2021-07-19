package com.baeldung.findanelement;

import static org.junit.Assert.*;

import java.util.ArrayList;
import java.util.List;

import org.junit.Test;

public class FindACustomerInGivenListUnitTest {

    private static List<Customer> customers = new ArrayList<>();

    static {
        customers.add(new Customer(1, "Jack"));
        customers.add(new Customer(2, "James"));
        customers.add(new Customer(3, "Sam"));
    }

    private static FindACustomerInGivenList findACustomerInGivenList = new FindACustomerInGivenList();

    @Test
    public void givenAnIndex_whenFoundUsingGivenIndex_thenReturnCustomer() {
        Customer customer = findACustomerInGivenList.findUsingGivenIndex(0, customers);

        assertEquals(1, customer.getId());
    }

    @Test
    public void givenAnIndex_whenNotFoundUsingGivenIndex_thenReturnNull() {
        Customer customer = findACustomerInGivenList.findUsingGivenIndex(5, customers);

        assertNull(customer);
    }

    @Test
    public void givenACustomer_whenFoundUsingContains_thenReturnTrue() {
        Customer james = new Customer(2, "James");
        boolean isJamesPresent = findACustomerInGivenList.findUsingContains(james, customers);

        assertEquals(true, isJamesPresent);
    }

    @Test
    public void givenACustomer_whenNotFoundUsingContains_thenReturnFalse() {
        Customer john = new Customer(5, "John");
        boolean isJohnPresent = findACustomerInGivenList.findUsingContains(john, customers);

        assertEquals(false, isJohnPresent);
    }

    @Test
    public void givenACustomer_whenFoundUsingIndexOf_thenReturnItsIndex() {
        Customer james = new Customer(2, "James");
        int indexOfJames = findACustomerInGivenList.findUsingIndexOf(james, customers);

        assertEquals(1, indexOfJames);
    }

    @Test
    public void givenACustomer_whenNotFoundUsingIndexOf_thenReturnMinus1() {
        Customer john = new Customer(5, "John");
        int indexOfJohn = findACustomerInGivenList.findUsingIndexOf(john, customers);

        assertEquals(-1, indexOfJohn);
    }

    @Test
    public void givenName_whenCustomerWithNameFoundUsingIterator_thenReturnCustomer() {
        Customer james = findACustomerInGivenList.findUsingIterator("James", customers);

        assertEquals("James", james.getName());
        assertEquals(2, james.getId());
    }

    @Test
    public void givenName_whenCustomerWithNameNotFoundUsingIterator_thenReturnNull() {
        Customer john = findACustomerInGivenList.findUsingIterator("John", customers);

        assertNull(john);
    }

    @Test
    public void givenName_whenCustomerWithNameFoundUsingEnhancedFor_thenReturnCustomer() {
        Customer james = findACustomerInGivenList.findUsingEnhancedForLoop("James", customers);

        assertEquals("James", james.getName());
        assertEquals(2, james.getId());
    }

    @Test
    public void givenName_whenCustomerWithNameNotFoundUsingEnhancedFor_thenReturnNull() {
        Customer john = findACustomerInGivenList.findUsingEnhancedForLoop("John", customers);

        assertNull(john);
    }

    @Test
    public void givenName_whenCustomerWithNameFoundUsingStream_thenReturnCustomer() {
        Customer james = findACustomerInGivenList.findUsingStream("James", customers);

        assertEquals("James", james.getName());
        assertEquals(2, james.getId());
    }

    @Test
    public void givenName_whenCustomerWithNameNotFoundUsingStream_thenReturnNull() {
        Customer john = findACustomerInGivenList.findUsingStream("John", customers);

        assertNull(john);
    }

    @Test
    public void givenName_whenCustomerWithNameFoundUsingParallelStream_thenReturnCustomer() {
        Customer james = findACustomerInGivenList.findUsingParallelStream("James", customers);

        assertEquals("James", james.getName());
        assertEquals(2, james.getId());
    }

    @Test
    public void givenName_whenCustomerWithNameNotFoundUsingParallelStream_thenReturnNull() {
        Customer john = findACustomerInGivenList.findUsingParallelStream("John", customers);

        assertNull(john);
    }

    @Test
    public void givenName_whenCustomerWithNameFoundUsingApacheCommon_thenReturnCustomer() {
        Customer james = findACustomerInGivenList.findUsingApacheCommon("James", customers);

        assertEquals("James", james.getName());
        assertEquals(2, james.getId());
    }

    @Test
    public void givenName_whenCustomerWithNameNotFoundUsingApacheCommon_thenReturnNull() {
        Customer john = findACustomerInGivenList.findUsingApacheCommon("John", customers);

        assertNull(john);
    }

    @Test
    public void givenName_whenCustomerWithNameFoundUsingGuava_thenReturnCustomer() {
        Customer james = findACustomerInGivenList.findUsingGuava("James", customers);

        assertEquals("James", james.getName());
        assertEquals(2, james.getId());
    }

    @Test
    public void givenName_whenCustomerWithNameNotFoundUsingGuava_thenReturnNull() {
        Customer john = findACustomerInGivenList.findUsingGuava("John", customers);

        assertNull(john);
    }

}
