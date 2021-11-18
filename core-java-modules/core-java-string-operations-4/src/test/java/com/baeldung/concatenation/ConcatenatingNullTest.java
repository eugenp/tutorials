package com.baeldung.concatenation;

import static com.baeldung.concatenation.ConcatenatingNull.concatenateUsingCollectorsJoining;
import static com.baeldung.concatenation.ConcatenatingNull.concatenateUsingHelperMethod;
import static com.baeldung.concatenation.ConcatenatingNull.concatenateUsingJoin;
import static com.baeldung.concatenation.ConcatenatingNull.concatenateUsingPlusOperator;
import static com.baeldung.concatenation.ConcatenatingNull.concatenateUsingStringBuilder;
import static com.baeldung.concatenation.ConcatenatingNull.concatenateUsingStringConcat;
import static com.baeldung.concatenation.ConcatenatingNull.concatenateUsingStringJoiner;
import static org.junit.jupiter.api.Assertions.assertEquals;

import org.junit.jupiter.api.Test;

public class ConcatenatingNullTest {

    String[] values = { "Java ", null, "", "is ", "great!" };
    
    @Test
    public void givenStringElementsWithNull_whenConcatenatedUsingPlus_thenNullIsRemoved() {
        String result = concatenateUsingPlusOperator(values);
        assertEquals("Java is great!", result);
    }
    
    @Test
    public void givenStringElementsWithNull_whenConcatenatedUsingHelperMethod_thenNullIsRemoved() {
        String result = concatenateUsingHelperMethod(values);
        assertEquals("Java is great!", result);
    }
    
    @Test
    public void givenStringElementsWithNull_whenConcatenatedUsingStringBuilder_thenNullIsRemoved() {
        String result = concatenateUsingStringBuilder(values);
        assertEquals("Java is great!", result);
    }
    
    @Test
    public void givenStringElementsWithNull_whenConcatenatedUsingJoin_thenNullIsNotRemoved() {
        String result = concatenateUsingJoin(values);
        assertEquals("Java nullis great!", result);
    }
    
    @Test
    public void givenStringElementsWithNull_whenConcatenatedUsingStringJoiner_thenNullIsRemoved() {
        String result = concatenateUsingStringJoiner(values);
        assertEquals("Java is great!", result);
    }
    
    @Test
    public void givenStringElementsWithNull_whenConcatenatedUsingCollectorsJoining_thenNullIsRemoved() {
        String result = concatenateUsingCollectorsJoining(values);
        assertEquals("Java is great!", result);
    }
    
    @Test
    public void givenStringElementsWithNull_whenConcatenatedUsingStringConcat_thenNullIsRemoved() {
        String result = concatenateUsingStringConcat(values);
        assertEquals("Java is great!", result);
    }
}
