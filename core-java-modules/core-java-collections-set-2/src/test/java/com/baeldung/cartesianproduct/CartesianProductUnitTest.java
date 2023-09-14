package com.baeldung.cartesianproduct;

import static org.testng.Assert.assertEquals;

import java.util.ArrayList;
import java.util.Arrays;
import java.util.HashSet;
import java.util.List;
import java.util.Set;

import org.testng.annotations.Test;

public class CartesianProductUnitTest {
    private CartesianProduct cp = new CartesianProduct();
    List<List<Object>> sets = Arrays.asList(
        Arrays.asList(10, 20),
        Arrays.asList("John", "Jack"),
        Arrays.asList('I', 'J')
    );

    @Test
    public void whenUsingStreams_thenCalculateCartesianProduct() {
        List<List<Object>> expected = Arrays.asList(
            Arrays.asList(10, "John", 'I'),
            Arrays.asList(10, "John", 'J'),
            Arrays.asList(10, "Jack", 'I'),
            Arrays.asList(10, "Jack", 'J'),
            Arrays.asList(20, "John", 'I'),
            Arrays.asList(20, "John", 'J'),
            Arrays.asList(20, "Jack", 'I'),
            Arrays.asList(20, "Jack", 'J')
        );
        List<List<Object>> cartesianProduct = cp.getCartesianProductUsingStreams(sets);

        assertEquals(expected, cartesianProduct);
    }

    @Test
    public void whenUsingRecursion_thenCalculateCartesianProduct() {
        List<List<Object>> expected = Arrays.asList(
            Arrays.asList(10, "John", 'I'),
            Arrays.asList(10, "John", 'J'),
            Arrays.asList(10, "Jack", 'I'),
            Arrays.asList(10, "Jack", 'J'),
            Arrays.asList(20, "John", 'I'),
            Arrays.asList(20, "John", 'J'),
            Arrays.asList(20, "Jack", 'I'),
            Arrays.asList(20, "Jack", 'J')
        );
        List<List<Object>> cartesianProduct = cp.getCartesianProductRecursive(sets);

        assertEquals(expected, cartesianProduct);
    }

    @Test
    public void whenUsingIterativeApproach_thenCalculateCartesianProduct() {
        List<List<Object>> expected = Arrays.asList(
            Arrays.asList(20, "Jack", 'J'),
            Arrays.asList(10, "Jack", 'J'),
            Arrays.asList(20, "John", 'J'),
            Arrays.asList(10, "John", 'J'),
            Arrays.asList(20, "Jack", 'I'),
            Arrays.asList(10, "Jack", 'I'),
            Arrays.asList(20, "John", 'I'),
            Arrays.asList(10, "John", 'I')
        );
        List<List<Object>> cartesianProduct = cp.getCartesianProductIterative(sets);

        assertEquals(expected, cartesianProduct);
    }

    @Test
    public void whenUsingGuava_thenCalculateCartesianProduct() {
        List<Set<Object>> sets = new ArrayList<>();
        sets.add(new HashSet<>(Arrays.asList(10, 20)));
        sets.add(new HashSet<>(Arrays.asList("John", "Jack")));
        sets.add(new HashSet<>(Arrays.asList('I', 'J')));

        List<List<Object>> expected = Arrays.asList(
            Arrays.asList(20, "John", 'I'),
            Arrays.asList(20, "John", 'J'),
            Arrays.asList(20, "Jack", 'I'),
            Arrays.asList(20, "Jack", 'J'),
            Arrays.asList(10, "John", 'I'),
            Arrays.asList(10, "John", 'J'),
            Arrays.asList(10, "Jack", 'I'),
            Arrays.asList(10, "Jack", 'J')
        );
        List<List<Object>> cartesianProduct = cp.getCartesianProductUsingGuava(sets);

        assertEquals(expected, cartesianProduct);
    }
}