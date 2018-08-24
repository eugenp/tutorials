package com.baeldung.geode;

import com.baeldung.geode.functions.PrimeNumber;
import org.apache.geode.cache.Region;
import org.apache.geode.cache.client.ClientCache;
import org.apache.geode.cache.client.ClientCacheFactory;
import org.apache.geode.cache.client.ClientRegionShortcut;
import org.apache.geode.cache.execute.Execution;
import org.apache.geode.cache.execute.FunctionService;
import org.apache.geode.cache.execute.ResultCollector;
import org.apache.geode.cache.query.*;
import org.junit.After;
import org.junit.Before;
import org.junit.Test;

import java.util.*;
import java.util.function.Function;
import java.util.function.Supplier;
import java.util.stream.Collectors;
import java.util.stream.IntStream;
import java.util.stream.Stream;

import static org.junit.Assert.assertEquals;
import static org.junit.Assert.assertNotNull;

public class GeodeSamplesIntegrationTest {

    ClientCache cache = null;
    Region<String, String> region = null;
    Region<Integer, Customer> partitionedRegion = null;
    Region<Integer, Customer> queryRegion = null;
    Region<Integer, String> functionRegion = null;
    Region<CustomerKey, Customer> customKeyRegion = null;

    @Before
    public void connect() {
        this.cache = new ClientCacheFactory().addPoolLocator("localhost", 10334)
            .create();
        this.region = this.cache.<String, String> createClientRegionFactory(ClientRegionShortcut.CACHING_PROXY)
            .create("baeldung");
        this.partitionedRegion = this.cache.<Integer, Customer> createClientRegionFactory(ClientRegionShortcut.CACHING_PROXY)
            .create("baeldung-partition");
        this.queryRegion = this.cache.<Integer, Customer> createClientRegionFactory(ClientRegionShortcut.CACHING_PROXY)
            .create("baeldung-oql");
        this.functionRegion = this.cache.<Integer, String> createClientRegionFactory(ClientRegionShortcut.CACHING_PROXY)
            .create("baeldung-function");
        this.customKeyRegion = this.cache.<CustomerKey, Customer> createClientRegionFactory(ClientRegionShortcut.CACHING_PROXY)
            .create("baeldung-custom");
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

        this.customKeyRegion.putAll(customerInfo);

        Customer storedCustomer = this.customKeyRegion.get(key);
        assertEquals("William", storedCustomer.getFirstName());
        assertEquals("Russell", storedCustomer.getLastName());

    }

    @Test
    public void whenSaveCustomerDataOnPartitionedRegion_thenDataSavedCorrectly() {
        Customer customer1 = new Customer(new CustomerKey(1l), "Gheorge", "Manuc", 36);
        Customer customer2 = new Customer(new CustomerKey(2l), "Allan", "McDowell", 43);
        Customer customer3 = new Customer(new CustomerKey(3l), "Alan", "McClean", 23);
        Customer customer4 = new Customer(new CustomerKey(4l), "Allan", "Donald", 46);

        Map<Integer, Customer> customerData = new HashMap<>();
        customerData.put(1, customer1);
        customerData.put(2, customer2);
        customerData.put(3, customer3);
        customerData.put(4, customer4);

        this.partitionedRegion.putAll(customerData);
        // assert the size on the cache server.
        assertEquals(4, this.partitionedRegion.sizeOnServer());
    }

    @Test
    public void whenFindACustomerUsingOQL_thenCorrectCustomerObject() throws NameResolutionException, TypeMismatchException, QueryInvocationTargetException, FunctionDomainException {

        Customer customer1 = new Customer("Gheorge", "Manuc", 36);
        Customer customer2 = new Customer("Allan", "McDowell", 43);
        Customer customer3 = new Customer("Alan", "McClean", 23);
        Customer customer4 = new Customer("Allan", "Donald", 46);

        Map<Integer, Customer> customerData = new HashMap<>();
        customerData.put(1, customer1);
        customerData.put(2, customer2);
        customerData.put(3, customer3);
        customerData.put(4, customer4);

        this.queryRegion.putAll(customerData);
        // assert the size on the cache server.
        assertEquals(4, this.queryRegion.sizeOnServer());

        QueryService queryService = this.cache.getQueryService();
        String query = "select * from /baeldung-oql c where c.firstName = 'Allan'";
        SelectResults<Customer> queryResults = (SelectResults<Customer>) queryService.newQuery(query)
            .execute();
        assertEquals(2, queryResults.size());

    }

    @Test
    public void whenExecutePrimeNumberFunction_thenReturnOnlyPrimeNumbers() {

        Execution execution = FunctionService.onRegion(this.functionRegion);

        IntStream.rangeClosed(1, 5)
            .forEach(i -> this.functionRegion.put(i, String.valueOf(i)));

        ResultCollector<Integer, List> results = execution.execute(PrimeNumber.ID);
        Set<Integer> primes = new HashSet<>();
        List resultList = results.getResult();
        assertNotNull(resultList);
        assertEquals(1, resultList.size());

        primes.addAll((List<? extends Integer>) resultList.iterator()
            .next());
        assertEquals(4, primes.size());
    }

}
