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
import org.junit.Test;

import java.util.*;
import java.util.function.Function;
import java.util.stream.Collectors;
import java.util.stream.IntStream;

import static org.junit.Assert.assertEquals;
import static org.junit.Assert.assertNotNull;

public class GeodeSamplesIntegrationTest {

    @Test
    public void whenSendMessageToRegion_thenMessageSavedSuccessfully() {
        ClientCache cache = new ClientCacheFactory().addPoolLocator("localhost", 10334)
            .create();
        Region<String, String> region = cache.<String, String> createClientRegionFactory(ClientRegionShortcut.CACHING_PROXY)
            .create("baeldung");

        region.put("1", "Hello");
        region.put("2", "Baeldung");

        assertEquals("Hello", region.get("1"));
        assertEquals("Baeldung", region.get("2"));

        cache.close();
    }

    @Test
    public void whenPutMultipleValuesAtOnce_thenValuesSavedSuccessfully() {
        ClientCache cache = new ClientCacheFactory().addPoolLocator("localhost", 10334)
            .create();
        Region<String, String> region = cache.<String, String> createClientRegionFactory(ClientRegionShortcut.CACHING_PROXY)
            .create("baeldung");

        Map<String, String> values = IntStream.rangeClosed(1, 5)
            .mapToObj(String::valueOf)
            .collect(Collectors.toMap(Function.identity(), i -> "value" + i));

        region.putAll(values);

        IntStream.rangeClosed(1, 5)
            .mapToObj(String::valueOf)
            .forEach(e -> {
                assertEquals("value".concat(e), region.get(e));
            });
        cache.close();
    }

    @Test
    public void whenSaveCustomerDataOnPartitionedRegion_thenDataSavedCorrectly() {
        ClientCache cache = new ClientCacheFactory().addPoolLocator("localhost", 10334)
            .create();
        Region<Integer, Customer> region = cache.<Integer, Customer> createClientRegionFactory(ClientRegionShortcut.CACHING_PROXY)
            .create("baeldung-partition");

        Customer customer1 = new Customer(new CustomerKey(1l), "Gheorge", "Manuc", 36);
        Customer customer2 = new Customer(new CustomerKey(2l), "Allan", "McDowell", 43);
        Customer customer3 = new Customer(new CustomerKey(3l), "Alan", "McClean", 23);
        Customer customer4 = new Customer(new CustomerKey(4l), "Allan", "Donald", 46);

        Map<Integer, Customer> customerData = new HashMap<>();
        customerData.put(1, customer1);
        customerData.put(2, customer2);
        customerData.put(3, customer3);
        customerData.put(4, customer4);

        region.putAll(customerData);
        // assert the size on the cache server.
        assertEquals(4, region.sizeOnServer());
        cache.close();
    }

    @Test
    public void whenFindACustomerUsingOQL_thenCorrectCustomerObject() throws NameResolutionException, TypeMismatchException, QueryInvocationTargetException, FunctionDomainException {
        ClientCache cache = new ClientCacheFactory().addPoolLocator("localhost", 10334)
            .create();
        Region<Integer, Customer> region = cache.<Integer, Customer> createClientRegionFactory(ClientRegionShortcut.CACHING_PROXY)
            .create("baeldung-oql");

        Customer customer1 = new Customer("Gheorge", "Manuc", 36);
        Customer customer2 = new Customer("Allan", "McDowell", 43);
        Customer customer3 = new Customer("Alan", "McClean", 23);
        Customer customer4 = new Customer("Allan", "Donald", 46);

        Map<Integer, Customer> customerData = new HashMap<>();
        customerData.put(1, customer1);
        customerData.put(2, customer2);
        customerData.put(3, customer3);
        customerData.put(4, customer4);

        region.putAll(customerData);
        // assert the size on the cache server.
        assertEquals(4, region.sizeOnServer());

        QueryService queryService = cache.getQueryService();
        String query = "select * from /baeldung-oql c where c.firstName = 'Allan'";
        SelectResults<Customer> queryResults = (SelectResults<Customer>) queryService.newQuery(query)
            .execute();
        assertEquals(2, queryResults.size());

        cache.close();

    }

    @Test
    public void whenExecutePrimeNumberFunction_thenReturnOnlyPrimeNumbers() {
        // connect to the locator using default port 10334
        ClientCache cache = new ClientCacheFactory().addPoolLocator("localhost", 10334)
            .create();

        // create a local region that matches the server region
        Region<Integer, String> region = cache.<Integer, String> createClientRegionFactory(ClientRegionShortcut.CACHING_PROXY)
            .create("baeldung-function");

        Execution execution = FunctionService.onRegion(region);

        IntStream.rangeClosed(1, 5)
            .forEach(i -> region.put(i, String.valueOf(i)));

        ResultCollector<Integer, List> results = execution.execute(PrimeNumber.ID);
        Set<Integer> primes = new HashSet<>();
        List resultList = results.getResult();
        assertNotNull(resultList);
        assertEquals(1, resultList.size());

        primes.addAll((List<? extends Integer>) resultList.iterator()
            .next());
        assertEquals(4, primes.size());
        cache.close();
    }

}
