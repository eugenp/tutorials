package com.baeldung.commons.collections;

import com.baeldung.commons.collectionutil.Address;
import com.baeldung.commons.collectionutil.Customer;
import org.apache.commons.collections4.CollectionUtils;
import org.apache.commons.collections4.Predicate;
import org.apache.commons.collections4.Transformer;
import org.junit.Before;
import org.junit.Test;

import java.util.ArrayList;
import java.util.Arrays;
import java.util.Collection;
import java.util.LinkedList;
import java.util.List;

import static org.junit.Assert.assertEquals;
import static org.junit.Assert.assertFalse;
import static org.junit.Assert.assertTrue;

public class CollectionUtilsGuideUnitTest {

    Customer customer1 = new Customer(1, "Daniel", 123456l, "locality1", "city1", "1234");
    Customer customer4 = new Customer(4, "Bob", 456789l, "locality4", "city4", "4567");
    List<Customer> list1, list2, list3, linkedList1;

    @Before
    public void setup() {
        Customer customer2 = new Customer(2, "Fredrik", 234567l, "locality2", "city2", "2345");
        Customer customer3 = new Customer(3, "Kyle", 345678l, "locality3", "city3", "3456");
        Customer customer5 = new Customer(5, "Cat", 567890l, "locality5", "city5", "5678");
        Customer customer6 = new Customer(6, "John", 678901l, "locality6", "city6", "6789");

        list1 = Arrays.asList(customer1, customer2, customer3);
        list2 = Arrays.asList(customer4, customer5, customer6);
        list3 = Arrays.asList(customer1, customer2);

        linkedList1 = new LinkedList<>(list1);
    }

    @Test
    public void givenList_whenAddIgnoreNull_thenNoNullAdded() {
        CollectionUtils.addIgnoreNull(list1, null);
        assertFalse(list1.contains(null));
    }

    @Test
    public void givenTwoSortedLists_whenCollated_thenSorted() {
        List<Customer> sortedList = CollectionUtils.collate(list1, list2);

        assertEquals(6, sortedList.size());
        assertTrue(sortedList.get(0).getName().equals("Bob"));
        assertTrue(sortedList.get(2).getName().equals("Daniel"));
    }

    @Test
    public void givenListOfCustomers_whenTransformed_thenListOfAddress() {
        Collection<Address> addressCol = CollectionUtils.collect(list1, new Transformer<Customer, Address>() {
            public Address transform(Customer customer) {
                return new Address(customer.getLocality(), customer.getCity(), customer.getZip());
            }
        });

        List<Address> addressList = new ArrayList<>(addressCol);
        assertTrue(addressList.size() == 3);
        assertTrue(addressList.get(0).getLocality().equals("locality1"));
    }

    @Test
    public void givenCustomerList_whenFiltered_thenCorrectSize() {

        boolean isModified = CollectionUtils.filter(linkedList1, new Predicate<Customer>() {
            public boolean evaluate(Customer customer) {
                return Arrays.asList("Daniel", "Kyle").contains(customer.getName());
            }
        });

        // filterInverse does the opposite. It removes the element from the list if the Predicate returns true
        // select and selectRejected work the same way except that they do not remove elements from the given collection and return a new collection

        assertTrue(isModified && linkedList1.size() == 2);
    }

    @Test
    public void givenNonEmptyList_whenCheckedIsNotEmpty_thenTrue() {
        List<Customer> emptyList = new ArrayList<>();
        List<Customer> nullList = null;

        // Very handy at times where we want to check if a collection is not null and not empty too.
        // isNotEmpty does the opposite. Handy because using ! operator on isEmpty makes it missable while reading
        assertTrue(CollectionUtils.isNotEmpty(list1));
        assertTrue(CollectionUtils.isEmpty(nullList));
        assertTrue(CollectionUtils.isEmpty(emptyList));
    }

    @Test
    public void givenCustomerListAndASubcollection_whenChecked_thenTrue() {
        assertTrue(CollectionUtils.isSubCollection(list3, list1));
    }

    @Test
    public void givenTwoLists_whenIntersected_thenCheckSize() {
        Collection<Customer> intersection = CollectionUtils.intersection(list1, list3);
        assertTrue(intersection.size() == 2);
    }

    @Test
    public void givenTwoLists_whenSubtracted_thenCheckElementNotPresentInA() {
        Collection<Customer> result = CollectionUtils.subtract(list1, list3);
        assertFalse(result.contains(customer1));
    }

    @Test
    public void givenTwoLists_whenUnioned_thenCheckElementPresentInResult() {
        Collection<Customer> union = CollectionUtils.union(list1, list2);
        assertTrue(union.contains(customer1));
        assertTrue(union.contains(customer4));
    }

}