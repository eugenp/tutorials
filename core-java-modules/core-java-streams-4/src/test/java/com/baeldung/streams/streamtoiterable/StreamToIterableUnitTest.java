package com.baeldung.streams.streamtoiterable;

import java.util.ArrayList;
import java.util.List;

import org.junit.Assert;
import org.junit.Test;

public class StreamToIterableUnitTest {

    @Test
    public void whenLambdaIsUsedThenStreamAsIterable(){
        StreamToIterable streamToIterable = new StreamToIterable();
        String actualString =  streamToIterable.streamToIterableLambda(getListOfStrings());
        String expectedString = "This is a sentence with no punctuation";
        Assert.assertEquals(expectedString, actualString);
    }

    @Test
    public void whenMethodReferenceIsUsedThenStreamAsIterable(){
        StreamToIterable streamToIterable = new StreamToIterable();
        String actualString =  streamToIterable.streamToIterableMethodReference(getListOfStrings());
        String expectedString = "This is a sentence with no punctuation";
        Assert.assertEquals(expectedString, actualString);
    }

    @Test
    public void whenCollectedToListThenStreamAsIterable(){
        StreamToIterable streamToIterable = new StreamToIterable();
        String actualString =  streamToIterable.streamToList(getListOfStrings());
        String expectedString = "This is a sentence with no punctuation";
        Assert.assertEquals(expectedString, actualString);
    }

    private List<String> getListOfStrings(){
        List<String> listOfStrings = new ArrayList<>();
        listOfStrings.add("This ");
        listOfStrings.add("is ");
        listOfStrings.add("a ");
        listOfStrings.add(null);
        listOfStrings.add("sentence ");
        listOfStrings.add("with ");
        listOfStrings.add("no ");
        listOfStrings.add(null);
        listOfStrings.add("punctuation");
        return listOfStrings;
    }
}
