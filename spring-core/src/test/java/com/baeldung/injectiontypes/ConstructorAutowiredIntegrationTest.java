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
import com.baeldung.dependency.injectiontypes.HotelService;

@RunWith(SpringJUnit4ClassRunner.class)
@ContextConfiguration(loader = AnnotationConfigContextLoader.class, classes = ApplicationContextTestAutowiredName.class)
public class ConstructorAutowiredIntegrationTest {

    @Autowired
    private HotelService hotelService;

    @Test
    public void givenConstructorAutowiredHotelService_WhenCallPrint_ThenPrintHelloMessage() {
        assertNotNull(hotelService);
        assertEquals("Hello from Hotel Service", hotelService.print());
    }

    @Test
    public void givenConstructorAutowiredHotelService_WhenCallPrintOnRoomService_ThenPrintHelloMessage() {
        assertNotNull(hotelService.getRoomService());
        assertEquals("Hello from Room Service", hotelService.getRoomService()
            .print());
    }
}
