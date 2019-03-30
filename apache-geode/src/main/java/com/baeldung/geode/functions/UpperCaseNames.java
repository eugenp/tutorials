package com.baeldung.geode.functions;

import com.baeldung.geode.Customer;
import com.baeldung.geode.CustomerKey;
import org.apache.geode.cache.Region;
import org.apache.geode.cache.execute.Function;
import org.apache.geode.cache.execute.FunctionContext;
import org.apache.geode.cache.execute.RegionFunctionContext;

import java.util.Map;

public class UpperCaseNames implements Function<Boolean> {
    private static final long serialVersionUID = -8946294032165677602L;

    @Override
    public void execute(FunctionContext<Boolean> context) {
        RegionFunctionContext regionContext = (RegionFunctionContext) context;
        Region<CustomerKey, Customer> region = regionContext.getDataSet();

        for (Map.Entry<CustomerKey, Customer> entry : region.entrySet()) {
            Customer customer = entry.getValue();
            customer.setFirstName(customer.getFirstName()
                .toUpperCase());
        }

        context.getResultSender()
            .lastResult(true);
    }

    @Override
    public String getId() {
        return getClass().getName();
    }
}
