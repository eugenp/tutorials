package com.baeldung.mathematics.integer.sequence;

import jakarta.enterprise.context.ApplicationScoped;

import java.util.ArrayList;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

@ApplicationScoped
public class FibonacciService {
    private static final Logger logger = LoggerFactory.getLogger(FibonacciService.class);

    public ArrayList<Integer> generateSequence(int nthNumber) {
        int firstInteger = 0, secondInteger = 1;
        
        ArrayList<Integer> generatedSequence = new ArrayList<>();
        generatedSequence.add(firstInteger);
        generatedSequence.add(secondInteger);
        for (int i = 2; i <= nthNumber; i++) {
            int next = firstInteger + secondInteger;
            generatedSequence.add(next);
            firstInteger = secondInteger;
            secondInteger = next;
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
