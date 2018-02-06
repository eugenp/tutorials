package com.baeldung.rxjava.combine;

import static org.hamcrest.Matchers.hasItems;
import static org.junit.Assert.assertEquals;
import static org.junit.Assert.assertFalse;
import static org.junit.Assert.assertThat;

import java.util.ArrayList;
import java.util.List;

import org.junit.Test;

import rx.Observable;

public class ObservableZipTest {

    @Test
    public void testZip() {
        List<String> zippedStrings = new ArrayList<>();
        Observable.zip(
            Observable.from(new String[] { "Simple", "Moderate", "Complex" }), 
            Observable.from(new String[] { "Solutions", "Success", "Heirarchy"}),
        (str1, str2) -> {
            return str1 + " " + str2;
        }).subscribe(zipped -> {
            zippedStrings.add(zipped);
        });
        
        assertFalse(zippedStrings.isEmpty());
        assertEquals(3, zippedStrings.size());
        assertThat(zippedStrings, hasItems("Simple Solutions", "Moderate Success", "Complex Heirarchy"));
    }
}
