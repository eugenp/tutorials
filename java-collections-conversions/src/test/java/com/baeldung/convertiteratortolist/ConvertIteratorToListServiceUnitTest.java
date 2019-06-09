package com.baeldung.convertiteratortolist;

import java.util.Arrays;
import java.util.Iterator;
import java.util.List;

import org.junit.Before;
import org.junit.Test;

import static org.hamcrest.MatcherAssert.assertThat;
import static org.hamcrest.CoreMatchers.*;

public class ConvertIteratorToListServiceUnitTest {

    Iterator<Integer> iterator;
    List<Integer> expectedList;
    private ConvertIteratorToListService convertIteratorToListService;

    @Before
    public void setUp() throws Exception {
        convertIteratorToListService = new ConvertIteratorToListService();
        expectedList = Arrays.asList(1, 2, 3, 4, 5);
        iterator = expectedList.iterator();
    }

    @Test
    public void givenAnIterator_whenConvertIteratorToListBeforeJava8_thenReturnAList() {
        List<Integer> actualList = convertIteratorToListService.convertIteratorToListBeforeJava8(iterator);
        assertThat(expectedList.size(), is(actualList.size()));
        assertThat(expectedList, is(actualList));

    }

    @Test
    public void givenAnIterator_whenConvertIteratorToListAfterJava8_thenReturnAList() {
        List<Integer> actualList = convertIteratorToListService.convertIteratorToListAfterJava8(iterator);
        assertThat(expectedList.size(), is(actualList.size()));
        assertThat(expectedList, is(actualList));

    }

    @Test
    public void givenAnIterator_whenConvertIteratorToListJava8Stream_thenReturnAList() {
        List<Integer> actualList = convertIteratorToListService.convertIteratorToListJava8Stream(iterator);
        assertThat(expectedList.size(), is(actualList.size()));
        assertThat(expectedList, is(actualList));

    }

    @Test
    public void givenAnIterator_whenConvertIteratorToImmutableListWithGuava_thenReturnAList() {
        List<Integer> actualList = convertIteratorToListService.convertIteratorToImmutableListWithGuava(iterator);
        assertThat(expectedList.size(), is(actualList.size()));
        assertThat(expectedList, is(actualList));

    }

    @Test
    public void givenAnIterator_whenConvertIteratorToMutableListWithGuava_thenReturnAList() {
        List<Integer> actualList = convertIteratorToListService.convertIteratorToMutableListWithGuava(iterator);
        assertThat(expectedList.size(), is(actualList.size()));
        assertThat(expectedList, is(actualList));

    }

    @Test
    public void givenAnIterator_whenConvertIteratorToMutableListWithApacheCommons_thenReturnAList() {
        List<Integer> actualList = convertIteratorToListService.convertIteratorToMutableListWithApacheCommons(iterator);
        assertThat(expectedList.size(), is(actualList.size()));
        assertThat(expectedList, is(actualList));

    }

}
