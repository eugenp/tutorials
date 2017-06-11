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
import com.baeldung.dependency.injectiontypes.RoomService;

@RunWith(SpringJUnit4ClassRunner.class)
@ContextConfiguration(loader = AnnotationConfigContextLoader.class, classes = ApplicationContextTestAutowiredName.class)
public class SetterAutowiredIntegrationTest {

    @Autowired
    private RoomService roomService;

    @Test
    public void givenSetterAutowiredHRoomService_WhenCallPrint_ThenPrintHelloMessage() {
        assertNotNull(roomService);
        assertEquals("Hello from Room Service", roomService.print());
    }

    @Test
    public void givenSetterAutowiredHRoomService_WhenCallPrintOnBedService_ThenPrintHelloMessage() {
        assertNotNull(roomService.getBedService());
        assertEquals("Hello from Bed Service", roomService.getBedService()
            .print());
    }
}
