package com.hexarch.controller;

import com.hexarch.domain.Item;
import com.hexarch.jpa.ItemRepository;
import com.hexarch.service.ItemService;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.junit.MockitoJUnitRunner;

import javax.swing.text.html.Option;

import java.util.Optional;

import static org.junit.Assert.assertFalse;
import static org.junit.Assert.assertTrue;
import static org.mockito.ArgumentMatchers.any;
import static org.mockito.Mockito.when;

@RunWith(MockitoJUnitRunner.class)
public class ItemServiceTest {

    @Mock
    ItemRepository itemRepository;

    @InjectMocks
    ItemService itemService;

    @Test
    public void checkItemAvailable_nullObject(){

        boolean result = itemService.checkItemAvailable(null);
        assertFalse(result);
    }

    @Test
    public void checkItemAvailable_nullId(){

        boolean result = itemService.checkItemAvailable(new Item());
        assertFalse(result);
    }

    @Test
    public void checkItemAvailable_valueNotPresent(){

        when(itemRepository.findById(any())).thenReturn(Optional.empty());
        Item item = new Item(100,"SKU001", "Iphone XR", 999.99);
        boolean result = itemService.checkItemAvailable(item);
        assertFalse(result);
    }

    @Test
    public void checkItemAvailable_valuePresent(){
        Item item = new Item(100,"SKU001", "Iphone XR", 999.99);
        when(itemRepository.findById(any())).thenReturn(Optional.ofNullable(item));
        boolean result = itemService.checkItemAvailable(item);
        assertTrue(result);
    }

}
