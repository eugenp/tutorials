package collectionutils;

import static org.junit.Assert.assertFalse;
import static org.junit.Assert.assertTrue;

import java.util.ArrayList;
import java.util.Arrays;
import java.util.Collection;
import java.util.LinkedList;
import java.util.List;

import org.apache.commons.collections4.Closure;
import org.apache.commons.collections4.CollectionUtils;
import org.apache.commons.collections4.Predicate;
import org.apache.commons.collections4.Transformer;
import org.junit.Test;

import com.baeldung.collectionutilsguide.model.Address;
import com.baeldung.collectionutilsguide.model.Customer;

public class CollectionUtilsGuideTest {
    
    Customer customer1 = new Customer(1, "Daniel", "locality1", "city1");
    Customer customer2 = new Customer(2, "Fredrik", "locality2", "city2");
    Customer customer3 = new Customer(3, "Kyle", "locality3", "city3");
    Customer customer4 = new Customer(4, "Bob", "locality4", "city4");
    Customer customer5 = new Customer(5, "Cat", "locality5", "city5");
    Customer customer6 = new Customer(6, "John", "locality6", "city6");
    
    List<Customer> list1 = Arrays.asList(customer1, customer2, customer3);
    List<Customer> list2 = Arrays.asList(customer4, customer5, customer6);
    List<Customer> list3 = Arrays.asList(customer1, customer2);
    
    List<Customer> linkedList1 = new LinkedList<Customer>(list1);
    
    @Test
    public void givenList_WhenAddIgnoreNull_thenNoNullAdded() {
        CollectionUtils.addIgnoreNull(list1, null);
        assertFalse(list1.contains(null));
    }
    
    @Test
    public void givenTwoSortedLists_WhenCollated_thenSorted() {
        List<Customer> sortedList = CollectionUtils.collate(list1, list2);
        assertTrue(sortedList.get(0).getName().equals("Bob"));
        assertTrue(sortedList.get(2).getName().equals("Daniel"));
    }
    
    @Test
    public void givenListOfCustomers_whenTransformed_thenListOfAddress() {
        Collection<Address> addressCol = CollectionUtils.collect(list1, new Transformer<Customer, Address>() {
            public Address transform(Customer customer) {
                return customer.getAddress();
            }
        });
        
        List<Address> addressList = new ArrayList<Address>(addressCol);
        assertTrue(addressList.size() == 3);
        assertTrue(addressList.get(0).getLocality().equals("locality1"));
    }
    
    @Test
    public void givenCustomerList_WhenCountMatches_thenCorrect() {
        int result = CollectionUtils.countMatches(list1, new Predicate<Customer>() {
            public boolean evaluate(Customer customer) {
                return Arrays.asList("Daniel","Kyle").contains(customer.getName());
            }
        });
        assertTrue(result == 2);
    }
    
    @Test
    public void givenCustomerList_WhenFiltered_thenCorrectSize() {
        
        boolean isModified = CollectionUtils.filter(linkedList1, new Predicate<Customer>() {
            public boolean evaluate(Customer customer) {
                return Arrays.asList("Daniel","Kyle").contains(customer.getName());
            }
        });
        
        //filterInverse does the opposite. It removes the element from the list if the Predicate returns true
        //select and selectRejected work the same way except that they do not remove elements from the given collection and return a new collection 
        
        assertTrue(isModified && linkedList1.size() == 2);
    }
    
    @Test
    public void givenCustomerList_WhenForAllDoSetNameNull_thenNameNull() {
        CollectionUtils.forAllDo(list1, new Closure<Customer>() {
            public void execute(Customer customer) {
                customer.setName(null);
            }
        });
        
        // forAllButLast does the same except for the last element in the collection
        assertTrue(list1.get(0).getName() == null);
    }
    
    @Test
    public void givenNonEmptyList_WhenCheckedIsNotEmpty_thenTrue() {
        List<Customer> emptyList = new ArrayList<Customer>();
        List<Customer> nullList = null;
        
        //Very handy at times where we want to check if a collection is not null and not empty too.
        //isNotEmpty does the opposite. Handy because using ! operator on isEmpty makes it missable while reading
        assertTrue(CollectionUtils.isNotEmpty(list1));
        assertTrue(CollectionUtils.isEmpty(nullList));
        assertTrue(CollectionUtils.isEmpty(emptyList));
    }
    
    @Test
    public void givenCustomerListAndASubcollection_WhenChecked_thenTrue() {
        assertTrue(CollectionUtils.isSubCollection(list3, list1));
    }
    
    @Test
    public void givenTwoLists_WhenIntersected_thenCheckSize() {
        Collection<Customer> intersection = CollectionUtils.intersection(list1, list3);
        assertTrue(intersection.size() == 2);
    }
    
    @Test
    public void givenTwoLists_WhenSubtracted_thenCheckElementNotPresentInA() {
        Collection<Customer> result = CollectionUtils.subtract(list1, list3);
        assertFalse(result.contains(customer1));
    }
    
    @Test
    public void givenTwoLists_WhenUnioned_thenCheckElementPresentInResult() {
        Collection<Customer> union = CollectionUtils.union(list1, list2);
        assertTrue(union.contains(customer1));
        assertTrue(union.contains(customer4));
    }
    
}
