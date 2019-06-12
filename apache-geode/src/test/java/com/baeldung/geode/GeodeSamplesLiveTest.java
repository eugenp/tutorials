package com.baeldung.geode;

import com.baeldung.geode.functions.UpperCaseNames;
import org.apache.geode.cache.Region;
import org.apache.geode.cache.client.ClientCache;
import org.apache.geode.cache.client.ClientCacheFactory;
import org.apache.geode.cache.client.ClientRegionShortcut;
import org.apache.geode.cache.execute.Execution;
import org.apache.geode.cache.execute.FunctionService;
import org.apache.geode.cache.query.*;
import org.junit.After;
import org.junit.Before;
import org.junit.Test;

import java.util.HashMap;
import java.util.Map;
import java.util.function.Function;
import java.util.function.Supplier;
import java.util.stream.Collectors;
import java.util.stream.Stream;

import static org.junit.Assert.assertEquals;

public class GeodeSamplesLiveTest {

    ClientCache cache = null;
    Region<String, String> region = null;
    Region<Integer, Customer> queryRegion = null;
    Region<CustomerKey, Customer> customerRegion = null;

    @Before
    public void connect() {
        this.cache = new ClientCacheFactory().addPoolLocator("localhost", 10334)
            .create();
        this.region = this.cache.<String, String> createClientRegionFactory(ClientRegionShortcut.CACHING_PROXY)
            .create("baeldung");
        this.customerRegion = this.cache.<CustomerKey, Customer> createClientRegionFactory(ClientRegionShortcut.CACHING_PROXY)
            .create("baeldung-customers");
    }

    @After
    public void cleanup() {
        this.cache.close();
    }

    @Test
    public void whenSendMessageToRegion_thenMessageSavedSuccessfully() {

        this.region.put("1", "Hello");
        this.region.put("2", "Baeldung");

        assertEquals("Hello", region.get("1"));
        assertEquals("Baeldung", region.get("2"));

    }

    @Test
    public void whenPutMultipleValuesAtOnce_thenValuesSavedSuccessfully() {

        Supplier<Stream<String>> keys = () -> Stream.of("A", "B", "C", "D", "E");
        Map<String, String> values = keys.get()
            .collect(Collectors.toMap(Function.identity(), String::toLowerCase));

        this.region.putAll(values);

        keys.get()
            .forEach(k -> assertEquals(k.toLowerCase(), this.region.get(k)));

    }

    @Test
    public void whenPutCustomKey_thenValuesSavedSuccessfully() {
        CustomerKey key = new CustomerKey(123);
        Customer customer = new Customer(key, "William", "Russell", 35);

        Map<CustomerKey, Customer> customerInfo = new HashMap<>();
        customerInfo.put(key, customer);

        this.customerRegion.putAll(customerInfo);

        Customer storedCustomer = this.customerRegion.get(key);
        assertEquals("William", storedCustomer.getFirstName());
        assertEquals("Russell", storedCustomer.getLastName());

    }

    @Test
    public void whenFindACustomerUsingOQL_thenCorrectCustomerObject() throws NameResolutionException, TypeMismatchException, QueryInvocationTargetException, FunctionDomainException {

        Map<CustomerKey, Customer> data = new HashMap<>();
        data.put(new CustomerKey(1), new Customer("Gheorge", "Manuc", 36));
        data.put(new CustomerKey(2), new Customer("Allan", "McDowell", 43));
        this.customerRegion.putAll(data);

        QueryService queryService = this.cache.getQueryService();
        String query = "select * from /baeldung-customers c where c.firstName = 'Allan'";
        SelectResults<Customer> queryResults = (SelectResults<Customer>) queryService.newQuery(query)
            .execute();
        assertEquals(1, queryResults.size());

    }

    @Test
    public void whenExecuteUppercaseNames_thenCustomerNamesAreUppercased() {
        Execution execution = FunctionService.onRegion(this.customerRegion);
        execution.execute(UpperCaseNames.class.getName());
        Customer customer = this.customerRegion.get(new CustomerKey(1));
        assertEquals("GHEORGE", customer.getFirstName());
    }
}
