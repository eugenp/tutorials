package com.baeldung.geode.functions;

import com.baeldung.geode.Customer;
import org.apache.geode.cache.Region;
import org.apache.geode.cache.execute.Function;
import org.apache.geode.cache.execute.FunctionContext;
import org.apache.geode.cache.execute.RegionFunctionContext;

import java.util.Comparator;
import java.util.Map;
import java.util.Optional;

public class CustomerWithMaxAge implements Function<Customer> {

    public static final String ID = CustomerWithMaxAge.class.getSimpleName();

    private static final long serialVersionUID = -6023734758827953742L;

    @Override
    public void execute(FunctionContext<Customer> context) {
        RegionFunctionContext regionContext = (RegionFunctionContext) context;
        Region<Integer, Customer> region = regionContext.getDataSet();

        Comparator<Customer> ageComparator = Comparator.comparing(Customer::getAge);

        Optional<Customer> customer = region.entrySet()
            .stream()
            .map(Map.Entry::getValue)
            .max(ageComparator);

        customer.ifPresent(c -> context.getResultSender()
            .lastResult(c));
    }

    @Override
    public String getId() {
        return ID;
    }
}
