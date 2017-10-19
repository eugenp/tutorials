package com.baeldung.java9.language;

import org.junit.Test;

import java.util.Collections;
import java.util.List;
import java.util.Objects;

import static org.hamcrest.Matchers.*;
import static org.hamcrest.MatcherAssert.assertThat;

public class Java9ObjectsAPIUnitTest {

    private List<String> aMethodReturningNullList(){
        return null;
    }

    @Test
    public void givenNullObject_whenRequireNonNullElse_thenElse(){
        List<String> aList = Objects.<List>requireNonNullElse(
                aMethodReturningNullList(), Collections.EMPTY_LIST);
        assertThat(aList, is(Collections.EMPTY_LIST));
    }

    private List<String> aMethodReturningNonNullList(){
        return List.of("item1", "item2");
    }

    @Test
    public void givenObject_whenRequireNonNullElse_thenObject(){
        List<String> aList = Objects.<List>requireNonNullElse(
                aMethodReturningNonNullList(), Collections.EMPTY_LIST);
        assertThat(aList, is(List.of("item1", "item2")));
    }

    @Test(expected = NullPointerException.class)
    public void givenNull_whenRequireNonNullElse_thenException(){
        Objects.<List>requireNonNullElse(null, null);
    }

    @Test
    public void givenObject_whenRequireNonNullElseGet_thenObject(){
        List<String> aList = Objects.<List>requireNonNullElseGet(null, List::of);
        assertThat(aList, is(List.of()));
    }

    @Test
    public void givenNumber_whenInvokeCheckIndex_thenNumber(){
        int length = 5;
        assertThat(Objects.checkIndex(4, length), is(4));
    }

    @Test(expected = IndexOutOfBoundsException.class)
    public void givenOutOfRangeNumber_whenInvokeCheckIndex_thenException(){
        int length = 5;
        Objects.checkIndex(5, length);
    }


    @Test
    public void givenSubRange_whenCheckFromToIndex_thenNumber(){
        int length = 6;
        assertThat(Objects.checkFromToIndex(2,length,length), is(2));
    }

    @Test(expected = IndexOutOfBoundsException.class)
    public void givenInvalidSubRange_whenCheckFromToIndex_thenException(){
        int length = 6;
        Objects.checkFromToIndex(2,7,length);
    }


    @Test
    public void givenSubRange_whenCheckFromIndexSize_thenNumber(){
        int length = 6;
        assertThat(Objects.checkFromIndexSize(2,3,length), is(2));
    }

    @Test(expected = IndexOutOfBoundsException.class)
    public void givenInvalidSubRange_whenCheckFromIndexSize_thenException(){
        int length = 6;
        Objects.checkFromIndexSize(2, 6, length);
    }


}
