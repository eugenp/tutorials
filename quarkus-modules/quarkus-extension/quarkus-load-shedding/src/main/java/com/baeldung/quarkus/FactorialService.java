package com.baeldung.quarkus;

import jakarta.enterprise.context.ApplicationScoped;

import java.util.ArrayList;
import java.util.List;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

@ApplicationScoped
public class FactorialService {
    private static final Logger logger = LoggerFactory.getLogger(FactorialService.class);

    public List<Long> generateSequence(int iterations) {
        long factorial = 1;

        List<Long> generatedSequence = new ArrayList<>();
        generatedSequence.add(factorial);
        for (int i = 1; i <= iterations; i++) {
            factorial *= i;
            generatedSequence.add(factorial);
        }

        try {
            int sleepTime = (int) (Math.random() * 14000) + 1000;
            logger.info("Sleeping for [" + sleepTime + "] milliseconds.");
            Thread.sleep(sleepTime);
        } catch (InterruptedException e) {
            e.printStackTrace();
        }

        return generatedSequence;
    }
}
