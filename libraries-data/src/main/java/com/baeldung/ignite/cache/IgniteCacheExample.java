package com.baeldung.ignite.cache;

import com.baeldung.ignite.model.Employee;
import org.apache.ignite.Ignite;
import org.apache.ignite.IgniteCache;
import org.apache.ignite.Ignition;
import org.apache.ignite.cache.query.QueryCursor;
import org.apache.ignite.cache.query.SqlFieldsQuery;
import org.apache.ignite.configuration.IgniteConfiguration;

import java.util.List;

public class IgniteCacheExample {

    public static void main(String[] args) {

        Ignite ignite = Ignition.ignite();

        IgniteCache<Integer, String> cache = ignite.cache("baeldungCache");

        cache.put(1, "baeldung cache value");

        String message = cache.get(1);
    }

    private static void getObjectFromCache(Ignite ignite) {

        IgniteCache<Integer, Employee> cache = ignite.getOrCreateCache("baeldungCache");

        cache.put(1, new Employee(1, "John", true));
        cache.put(2, new Employee(2, "Anna", false));
        cache.put(3, new Employee(3, "George", true));

        Employee employee = cache.get(1);
    }

    private static void getFromCacheWithSQl(Ignite ignite) {

        IgniteCache<Integer, Employee> cache = ignite.cache("baeldungCache");

        SqlFieldsQuery sql = new SqlFieldsQuery(
                "select name from Employee where isEmployed = 'true'");

        QueryCursor<List<?>> cursor = cache.query(sql);

        for (List<?> row : cursor) {
            System.out.println(row.get(0));
        }
    }

    private static void customInitialization() {

        IgniteConfiguration configuration = new IgniteConfiguration();
        configuration.setLifecycleBeans(new CustomLifecycleBean());
        Ignite ignite = Ignition.start(configuration);
    }

}
