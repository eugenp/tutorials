package com.baeldung.configurationproperties;

import static org.junit.jupiter.api.Assertions.assertEquals;

import java.time.Duration;

import org.junit.Test;
import org.junit.runner.RunWith;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.test.context.TestPropertySource;
import org.springframework.test.context.junit4.SpringRunner;
import org.springframework.util.unit.DataSize;

import com.baeldung.configurationproperties.PropertiesConversionApplication;
import com.baeldung.configurationproperties.PropertyConversion;

@RunWith(SpringRunner.class)
@SpringBootTest(classes = PropertiesConversionApplication.class)
@TestPropertySource("classpath:conversion.properties")
public class PropertiesConversionIntegrationTest {

    @Autowired
    private PropertyConversion properties;

    @Test
    public void whenUseTimeUnitPropertyConversion_thenSuccess() throws Exception {
        assertEquals(Duration.ofMillis(10), properties.getTimeInDefaultUnit());
        assertEquals(Duration.ofNanos(9), properties.getTimeInNano());
        assertEquals(Duration.ofDays(2), properties.getTimeInDays());
    }

    @Test
    public void whenUseDataSizePropertyConversion_thenSuccess() throws Exception {
        assertEquals(DataSize.ofBytes(300), properties.getSizeInDefaultUnit());
        assertEquals(DataSize.ofGigabytes(2), properties.getSizeInGB());
        assertEquals(DataSize.ofTerabytes(4), properties.getSizeInTB());        
    }
   
    @Test
    public void whenUseCustomPropertyConverter_thenSuccess() throws Exception {
        assertEquals("john", properties.getEmployee().getName());
        assertEquals(2000.0, properties.getEmployee().getSalary());
    }
    
}
