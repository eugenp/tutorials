package com.baeldung.common.web;

import java.util.ArrayList;
import java.util.Comparator;
import java.util.List;

import org.junit.Assert;
import org.junit.jupiter.api.Test;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageImpl;
import org.springframework.data.domain.PageRequest;
import org.springframework.data.domain.Pageable;

import com.baeldung.persistence.model.Foo;

public class ConvertListIntoPageUnitTest {

    @Test
    public void givenAList_whenQueried_thenReturnAPage() {
        // Create a pageable object
        Pageable pageable = createPageable(1, 2);
        // Create example list
        List<Foo> fooList = createExampleList();
        // Calculate indexes
        int start = calculateStartIndex(pageable);
        int end = calculateEndIndex(pageable, fooList, start);        
        // Create page
        Page<Foo> page = createPageWithListAsSourceAndPageable(pageable, fooList, start, end);
        // Validate
        validatePageableAndPage(pageable, start, end, page);
    }

    @Test
    public void givenASortedList_whenQueried_thenReturnAPage() {
        // Create a pageable object
        Pageable pageable = createPageable(2, 2);
        // Create example list
        List<Foo> fooList = createExampleList();
        // Calculate indexes
        int start = calculateStartIndex(pageable);
        int end = calculateEndIndex(pageable, fooList, start);
        // Sort list asc by name
        fooList.sort(Comparator.comparing(Foo::getName));
        // Create page
        Page<Foo> page = createPageWithListAsSourceAndPageable(pageable, fooList, start, end);
        // Validate
        validatePageableAndPage(pageable, start, end, page);
    }

    private PageImpl<Foo> createPageWithListAsSourceAndPageable(Pageable pageable, List<Foo> fooList, int start,
            int end) {
        return new PageImpl<Foo>(fooList.subList(start, end), pageable, fooList.size());
    }

    private int calculateStartIndex(Pageable pageable) {
        return (int) pageable.getOffset();
    }

    private int calculateEndIndex(Pageable pageable, List<Foo> fooList, int start) {
        return (int) ((start + pageable.getPageSize()) > fooList.size() ? fooList.size()
          : (start + pageable.getPageSize()));
    }

    private void validatePageableAndPage(Pageable pageable, int start, int end, Page<Foo> page) {
        Assert.assertEquals("Size is not the same", pageable.getPageSize(), page.getPageable().getPageSize());
        Assert.assertEquals("Page number is not the same", pageable.getPageNumber(),
                page.getPageable().getPageNumber());
        Assert.assertEquals("amount of objects returned does not match", end - start, page.getContent().size());
    }

    /*
     * This is a common list, it's just an example for the tests
     */
    public List<Foo> createExampleList() {
        List<Foo> foos = new ArrayList<Foo>();
        foos.add(new Foo("Foo 1"));
        foos.add(new Foo("Foo 2"));
        foos.add(new Foo("Foo 3"));
        foos.add(new Foo("Foo 4"));
        foos.add(new Foo("Foo 5"));
        foos.add(new Foo("Foo 6"));
        foos.add(new Foo("Foo 7"));
        return foos;
    }
    
    private Pageable createPageable(int pageNumber, int pageSize) {
        return PageRequest.of(pageNumber, pageSize);
    }
}