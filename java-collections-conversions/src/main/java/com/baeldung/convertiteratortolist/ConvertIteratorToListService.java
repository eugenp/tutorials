package com.baeldung.convertiteratortolist;

import java.util.ArrayList;
import java.util.Arrays;
import java.util.Iterator;
import java.util.List;
import java.util.stream.Collectors;
import java.util.stream.StreamSupport;

import org.apache.commons.collections4.IteratorUtils;

import com.google.common.collect.ImmutableList;
import com.google.common.collect.Lists;

public class ConvertIteratorToListService {
   

    public static void main(String[] args) {
        Iterator<Integer> iterator = Arrays.asList(1, 2, 3, 4, 5)
            .iterator();
        ConvertIteratorToListService convertIteratorToListService = new ConvertIteratorToListService();
        convertIteratorToListService.convertIteratorToListBeforeJava8(iterator);
        convertIteratorToListService.convertIteratorToListAfterJava8(iterator);
        convertIteratorToListService.convertIteratorToListJava8Stream(iterator);
        convertIteratorToListService.convertIteratorToImmutableListWithGuava(iterator);
        convertIteratorToListService.convertIteratorToMutableListWithGuava(iterator);
        convertIteratorToListService.convertIteratorToMutableListWithApacheCommons(iterator);
    
    }
      
    public List<Integer> convertIteratorToListBeforeJava8(Iterator<Integer> iterator) {

        List<Integer> list = new ArrayList<Integer>();

        //Convert Iterator to List using traditional while loop
        while (iterator.hasNext()) {
            list.add(iterator.next());
        }
        System.out.println(list);
        return list;
    }

    public List<Integer> convertIteratorToListAfterJava8(Iterator<Integer> iterator) {
        List<Integer> list = new ArrayList<Integer>();
        
        //Convert Iterator to List using Java 8
        iterator.forEachRemaining(list::add);
        return list;
    }

    public List<Integer> convertIteratorToListJava8Stream(Iterator<Integer> iterator) {
        
        // Convert iterator to iterable
        Iterable<Integer> iterable = () -> iterator;
        
        // Extract List from stream
        List<Integer> list = StreamSupport
            .stream(iterable.spliterator(), false)
            .collect(Collectors.toList());
        return list;
    }

    public List<Integer> convertIteratorToImmutableListWithGuava(Iterator<Integer> iterator) {

        // Convert Iterator to an Immutable list using Guava library in Java
        List<Integer> immutableList = ImmutableList.copyOf(iterator);
        return immutableList;
    }

    public List<Integer> convertIteratorToMutableListWithGuava(Iterator<Integer> iterator) {

        // Convert Iterator to a mutable list using Guava library in Java
        List<Integer> mutableList = Lists.newArrayList(iterator);
        return mutableList;
    }

    public List<Integer> convertIteratorToMutableListWithApacheCommons(Iterator<Integer> iterator) {

        // Convert Iterator to a mutable list using Apache Commons library in Java
        List<Integer> mutableList = IteratorUtils.toList(iterator);
        return mutableList;
    }

}
