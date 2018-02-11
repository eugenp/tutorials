package com.baeldung.demo;

import static org.junit.Assert.assertTrue;

import org.junit.Test;
import org.junit.runner.RunWith;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.test.context.ContextConfiguration;
import org.springframework.test.context.junit4.SpringRunner;

import com.baeldung.demo.inventory.InventoryService;
import com.baeldung.demo.profile.ClientProfileService;

@RunWith(SpringRunner.class)

@ContextConfiguration(classes = CheckOutService.class)
public class UnitTest {

    @Autowired
    private InventoryService inventoryService;

    @Autowired
    private ClientProfileService clientProfileService;

    @Test
    public void whenQueryInventory_thenReturn3() {
        assertTrue(inventoryService.returnIntentory("") == 3);
    }

    @Test
    public void whenQueryByUserId_thenReturnNames() {
     // @formatter:off
        assertTrue(
            "John".equals(clientProfileService.findByUserId("").getFirstName()));
        assertTrue(
            "Doe".equals(clientProfileService.findByUserId("").getLastName()));
     // @formatter:on
    }

}
