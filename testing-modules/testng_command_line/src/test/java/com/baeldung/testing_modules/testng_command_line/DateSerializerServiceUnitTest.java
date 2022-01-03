package com.baeldung.testing_modules.testng_command_line;

import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.util.Date;

import org.testng.Assert;
import org.testng.annotations.BeforeClass;
import org.testng.annotations.Test;

@Test(testName = "Date Serializer")
public class DateSerializerServiceUnitTest {

    private DateSerializerService toTest;

    public DateSerializerServiceUnitTest() {
        super();
    }

    @BeforeClass
    public void beforeClass() {
        toTest = new DateSerializerService();
    }

    @Test(groups = { "thread-safe", "system"}, expectedExceptions = {NullPointerException.class})
    void givenNullDate_whenSerializeDate_thenThrowsException() {
        //given
        Date dateToTest = null;

        //when
        toTest.serializeDate(dateToTest);

        // then
        // NullPointerException must be thrown
    }

    @Test(groups = { "thread-safe", "system"})
    void givenDateInstance_whenSerializeDate_thenSerializedDate() throws ParseException {
        // given
        String expected = "2021/01/01 00:00:00.000";
        SimpleDateFormat sdf = new SimpleDateFormat("yyyy/MM/dd HH:mm:ss.SSS");
        Date dateToTest = sdf.parse(expected);

        // when
        String serialized = toTest.serializeDate(dateToTest);
        
        // then
        Assert.assertEquals(expected, serialized);
    }
}
