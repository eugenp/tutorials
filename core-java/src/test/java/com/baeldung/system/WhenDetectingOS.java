package com.baeldung.system;

import org.junit.Assert;
import org.junit.Test;

import com.baeldung.system.DetectOS.OperatingSystem;

public class WhenDetectingOS {

    @Test
    public void whenUsingSystemProperty_shouldReturnOS() {
        OperatingSystem expected = OperatingSystem.Windows;
        DetectOS os = DetectOS.getInstance();
        OperatingSystem actual = os.getOperatingSystem();
        Assert.assertEquals(expected, actual);
    }
    
    @Test
    public void whenUsingSystemUtils_shouldReturnOS() {
        OperatingSystem expected = OperatingSystem.Windows;
        DetectOS os = DetectOS.getInstance();
        OperatingSystem actual = os.getOperatingSystemSystemUtils();
        Assert.assertEquals(expected, actual);
    }
}
