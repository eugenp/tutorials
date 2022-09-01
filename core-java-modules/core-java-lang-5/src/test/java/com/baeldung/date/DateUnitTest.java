package com.baeldung.date;

import java.util.Date;

import org.junit.Assert;
import org.junit.Test;

public class DateUnitTest {

    @Test
    public void whenUsingFullyQualifiedClassNames() {

        java.util.Date javaDate = new java.util.Date();
        com.baeldung.date.Date baeldungDate = new com.baeldung.date.Date(javaDate.getTime());

        Assert.assertEquals(javaDate.getTime(), baeldungDate.getTime());
    }

    @Test
    public void whenImportTheMostUsedOne() {

        Date javaDate = new Date();
        com.baeldung.date.Date baeldungDate = new com.baeldung.date.Date(javaDate.getTime());

        Assert.assertEquals(javaDate.getTime(), baeldungDate.getTime());
    }
}
