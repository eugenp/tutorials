package com.hexarch.controller;

import com.fasterxml.jackson.databind.ObjectMapper;
import com.hexarch.constants.ItemConstants;
import com.hexarch.domain.Item;
import com.hexarch.service.ItemService;
import org.junit.Before;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.MockitoAnnotations;
import org.mockito.junit.MockitoJUnitRunner;
import org.springframework.http.MediaType;
import org.springframework.test.web.servlet.MockMvc;
import org.springframework.test.web.servlet.setup.MockMvcBuilders;
import org.springframework.web.util.NestedServletException;
import org.springframework.web.util.UriComponentsBuilder;
import java.net.URI;
import static com.hexarch.constants.ItemConstants.ITEM_URL;
import static org.junit.Assert.assertEquals;
import static org.mockito.ArgumentMatchers.any;
import static org.mockito.Mockito.times;
import static org.mockito.Mockito.verify;
import static org.mockito.Mockito.when;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.*;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.status;

@RunWith(MockitoJUnitRunner.class)
public class ItemControllerTest {

    MockMvc mockMvc;

    @Mock
    ItemService itemService;

    @InjectMocks
    private ItemController itemController;


    @Before
    public void setUp() {

        MockitoAnnotations.initMocks(this);
        this.mockMvc = MockMvcBuilders.standaloneSetup(itemController).build();
    }

    @Test
    public void saveItem() throws Exception {
        Item item = new Item(100,"SKU001", "Iphone XR", 999.99);
         String itemBody = new ObjectMapper().writeValueAsString(item);
        when(itemService.saveItem(any())).thenReturn(item);

        mockMvc.perform(post((ITEM_URL)).content(itemBody).contentType(MediaType.APPLICATION_JSON))
                .andExpect(status().isCreated());

    }

    @Test
    public void updateItem() throws Exception {
        Item item = new Item(100,"SKU001", "Iphone XR", 999.99);
        String itemBody = new ObjectMapper().writeValueAsString(item);
        System.out.println(itemBody);
        when(itemService.checkItemAvailable(any())).thenReturn(true);
        when(itemService.updateItem(any())).thenReturn(item);

        mockMvc.perform(put((ITEM_URL)).content(itemBody).contentType(MediaType.APPLICATION_JSON))
                .andExpect(status().isOk());

    }

    @Test
    public void retrieveItem() throws Exception {

        //given
        Item item = new Item(201,"SKU001", "Iphone XR", 999.99);
        Integer itemId = 201;
        URI uri = UriComponentsBuilder.fromUriString(ItemConstants.SINGLE_ITEM).build(itemId);
        when(itemService.retrieveItem(any())).thenReturn(item);

        //when
        String reponse = mockMvc.perform(get((uri)).contentType(MediaType.APPLICATION_JSON))
                .andExpect(status().isOk())
        .andReturn().getResponse().getContentAsString();

        //then
        Item responseItem = new ObjectMapper().readValue(reponse, Item.class);
        assertEquals(itemId,responseItem.getId());

    }

    @Test
    public void deleteItem() throws Exception {

        //given
        Integer itemId = 201;
        URI uri = UriComponentsBuilder.fromUriString(ItemConstants.SINGLE_ITEM).build(itemId);

        //when
        mockMvc.perform(delete((uri)).contentType(MediaType.APPLICATION_JSON))
                .andExpect(status().isOk());

        //then
        verify(itemService,times(1)).deleteItem(any());
    }

    @Test(expected = NestedServletException.class)
    public void saveItem_RuntimeException() throws Exception {

        //given
        Item item = new Item(100,"SKU001", "Iphone XR", 999.99);
        String itemBody = new ObjectMapper().writeValueAsString(item);
        when(itemService.saveItem(any())).thenThrow(new RuntimeException("ExceptionOccurred"));

        //then
        mockMvc.perform(post((ITEM_URL)).content(itemBody).contentType(MediaType.APPLICATION_JSON))
                .andExpect(status().isInternalServerError());
    }
}
