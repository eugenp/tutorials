package com.baeldung.vavr.collections;

import static io.vavr.API.Array;
import static io.vavr.API.Failure;
import static io.vavr.API.List;
import static io.vavr.API.None;
import static io.vavr.API.Some;
import static io.vavr.API.Stream;
import static io.vavr.API.Success;
import static io.vavr.API.Tuple;
import static io.vavr.API.Vector;
import static org.junit.Assert.assertEquals;
import static org.junit.Assert.assertFalse;
import static org.junit.Assert.assertTrue;

import org.junit.Test;

import io.vavr.Tuple3;
import io.vavr.collection.Array;
import io.vavr.collection.List;
import io.vavr.collection.Stream;
import io.vavr.collection.Vector;
import io.vavr.control.Option;
import io.vavr.control.Try;

public class CollectionFactoryMethodsUnitTest {

    @Test
    public void givenANoneOptionElement_whenCreated_thenCorrect() {
        Option<Integer> none = None();
        assertFalse(none == null);
        assertEquals(none, Option.none());
    }

    @Test
    public void givenASomeOptionElement_whenCreated_thenCorrect() {
        Option<Integer> some = Some(1);
        assertFalse(some == null);
        assertTrue(some.contains(1));
    }

    @Test
    public void givenATupleElement_whenCreated_thenCorrect() {
        Tuple3<Character, String, Integer> tuple = Tuple('a', "chain", 2);
        assertTrue(tuple!=null);
        assertEquals(tuple._1(), new Character('a'));
        assertEquals(tuple._2(), "chain");
        assertEquals(tuple._3().intValue(), 2);
    }

    @Test
    public void givenASuccessObject_whenEvaluated_thenSuccess() {
        Try<Integer> integer = Success(55);
        assertEquals(integer.get().intValue(), 55);
    }
    @Test
    public void givenAFailureObject_whenEvaluated_thenExceptionThrown() {
        Try<Integer> failure = Failure(new Exception("Exception X encapsulated here"));
        
        try {
            Integer i = failure.get();// evaluate a failure raise the exception
            System.out.println(i);// not executed
        } catch (Exception e) {
            assertEquals(e.getMessage(), "Exception X encapsulated here");
        }
    }
    
    @Test
    public void givenAList_whenCreated_thenCorrect() {
        List<Integer> list = List(1, 2, 3, 4, 5);
        
        assertEquals(list.size(), 5);
        assertEquals(list.get(0).intValue(), 1);
    }
    
    @Test
    public void givenAnEmptyList_whenCreated_thenCorrect() {
        List<Integer> empty = List();
        
        assertEquals(empty.size(), 0);
        assertEquals(empty, List.empty());
    }
    
    @Test
    public void givenAnArray_whenCreated_thenCorrect() {
        Array<Integer> array = Array(1, 2, 3, 4, 5);
        
        assertEquals(array.size(), 5);
        assertEquals(array.get(0).intValue(), 1);
    }
    
    @Test
    public void givenAStream_whenCreated_thenCorrect() {
        Stream<Integer> stream = Stream(1, 2, 3, 4, 5);
        
        assertEquals(stream.size(), 5);
        assertEquals(stream.get(0).intValue(), 1);
    }
    
    @Test
    public void givenAVector_whenCreated_thenCorrect() {
        Vector<Integer> vector = Vector(1, 2, 3, 4, 5);
        
        assertEquals(vector.size(), 5);
        assertEquals(vector.get(0).intValue(), 1);
    }
}
