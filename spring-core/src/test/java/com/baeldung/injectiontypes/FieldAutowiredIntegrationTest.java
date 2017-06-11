package com.baeldung.injectiontypes;

import static org.junit.Assert.assertEquals;
import static org.junit.Assert.assertNotNull;

import org.junit.Test;
import org.junit.runner.RunWith;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.test.context.ContextConfiguration;
import org.springframework.test.context.junit4.SpringJUnit4ClassRunner;
import org.springframework.test.context.support.AnnotationConfigContextLoader;

import com.baeldung.configuration.ApplicationContextTestAutowiredName;
import com.baeldung.dependency.injectiontypes.BedService;

@RunWith(SpringJUnit4ClassRunner.class)
@ContextConfiguration(loader = AnnotationConfigContextLoader.class, classes = ApplicationContextTestAutowiredName.class)
public class FieldAutowiredIntegrationTest {

    @Autowired
    private BedService bedService;

    @Test
    public void givenSetterAutowiredHRoomService_WhenCallPrint_ThenPrintHelloMessage() {
        assertNotNull(bedService);
        assertEquals("Hello from Bed Service", bedService.print());
    }

    @Test
    public void givenSetterAutowiredHRoomService_WhenCallPrintOnBedService_ThenPrintHelloMessage() {
        assertNotNull(bedService.getAmenityService());
        assertEquals("Hello from Amenity Service", bedService.getAmenityService()
            .print());
    }
}
