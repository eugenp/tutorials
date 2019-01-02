package com.baeldung.system;

import org.junit.Assert;
import org.junit.Ignore;
import org.junit.Test;

@Ignore
public class WhenDetectingOSUnitTest {

    private DetectOS os = new DetectOS();

    @Test
    public void whenUsingSystemProperty_shouldReturnOS() {
        String expected = "Windows 10";
        String actual = os.getOperatingSystem();
        System.out.println("actual:{}" + actual);
        Assert.assertEquals(expected, actual);
    }

    @Test
    public void whenUsingSystemUtils_shouldReturnOS() {
        String expected = "Windows 10";
        String actual = os.getOperatingSystemSystemUtils();
        System.out.println("actual:{}" + actual);
        Assert.assertEquals(expected, actual);
    }
}
