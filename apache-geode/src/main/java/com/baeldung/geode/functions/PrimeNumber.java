package com.baeldung.geode.functions;

import org.apache.geode.cache.Region;
import org.apache.geode.cache.execute.Function;
import org.apache.geode.cache.execute.FunctionContext;
import org.apache.geode.cache.execute.RegionFunctionContext;

import java.util.ArrayList;
import java.util.Collections;
import java.util.List;
import java.util.Set;

public class PrimeNumber implements Function {

    public static final String ID = PrimeNumber.class.getSimpleName();

    @Override
    public void execute(FunctionContext context) {
        RegionFunctionContext regionContext = (RegionFunctionContext) context;
        Region<Integer, String> region = regionContext.getDataSet();

        List<Integer> primes = new ArrayList<>();
        Set<Integer> keys = region.keySet();
        for (Integer key : keys) {
            if (isPrime(key)) {
                primes.add(key);
            }
        }
        Collections.sort(primes);

        context.getResultSender()
            .lastResult(primes);
    }

    @Override
    public String getId() {
        return ID;
    }

    private boolean isPrime(int number) {
        int limit = (int) Math.floor(Math.sqrt(number));
        for (int divisor = 2; divisor <= limit; ++divisor) {
            if (number % divisor == 0) {
                return false;
            }
        }
        return true;
    }
}
