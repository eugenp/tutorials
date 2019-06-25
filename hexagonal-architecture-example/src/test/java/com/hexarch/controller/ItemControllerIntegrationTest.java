package com.hexarch.controller;


import com.hexarch.constants.ItemConstants;
import com.hexarch.domain.Item;
import com.hexarch.jpa.ItemRepository;
import com.hexarch.service.ItemService;
import org.junit.Before;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.boot.test.web.client.TestRestTemplate;
import org.springframework.http.HttpEntity;
import org.springframework.http.HttpMethod;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.test.annotation.DirtiesContext;
import org.springframework.test.context.junit4.SpringRunner;
import org.springframework.web.util.UriComponentsBuilder;

import java.net.URI;
import java.util.Arrays;
import java.util.List;

import static com.hexarch.constants.ItemConstants.ALL_ITEMS;
import static com.hexarch.constants.ItemConstants.ITEM_URL;
import static org.junit.Assert.assertEquals;


@RunWith(SpringRunner.class)
@SpringBootTest(webEnvironment = SpringBootTest.WebEnvironment.RANDOM_PORT)
public class ItemControllerIntegrationTest {

    @Autowired
    TestRestTemplate testRestTemplate;

    @Autowired
    ItemRepository itemRepository;

    @Autowired
    ItemService itemService;

    @Before
    public void setUp() {

        List<Item> itemList = Arrays.asList(new Item(201, "SKU100", "Samsung S10", 799.99),
                new Item(202, "SKU101", "Beats Wireless", 249.99));
        itemRepository.saveAll(itemList);
    }

    @Test
    public void saveItem() {

        //given
        Item item = new Item(100, "SKU001", "Iphone XR", 999.99);

        //when
        ResponseEntity<Item> itemResponseEntity = testRestTemplate.postForEntity(ITEM_URL, item, Item.class);

        //then
        assertEquals(HttpStatus.CREATED, itemResponseEntity.getStatusCode());
        ;
        assertEquals(100, itemResponseEntity.getBody().getId().intValue());

    }

    @Test
    public void retrieveAllItem() {

        //given
        Item item = new Item(100, "SKU001", "Iphone XR", 999.99);

        //when
        ResponseEntity<Item[]> itemResponseEntity = testRestTemplate.getForEntity(ALL_ITEMS, Item[].class);

        //then
        assertEquals(HttpStatus.OK, itemResponseEntity.getStatusCode());
        ;

    }

    @Test
    public void retrieveItem() {

        //given
        Integer itemId = 201;
        URI uri = UriComponentsBuilder.fromUriString(ItemConstants.SINGLE_ITEM).build(itemId);

        //when
        ResponseEntity<Item> itemResponseEntity = testRestTemplate.getForEntity(uri, Item.class);

        //then
        assertEquals(HttpStatus.OK, itemResponseEntity.getStatusCode());
        ;
        assertEquals("Samsung S10", itemResponseEntity.getBody().getItemDescription());
        assertEquals("SKU100", itemResponseEntity.getBody().getSku());

    }

    @Test
    public void updateItem() {

        //given
        Integer itemId = 201;
        Item itemToUpdate = new Item(201, "SKU100", "Samsung S10", 699.99);

        HttpEntity httpEntity = new HttpEntity(itemToUpdate);
        //when
        ResponseEntity<Item> itemResponseEntity = testRestTemplate.exchange(ITEM_URL, HttpMethod.PUT, httpEntity, Item.class);
        Item updatedItem = itemRepository.findById(itemId).get();

        //then
        assertEquals(HttpStatus.OK, itemResponseEntity.getStatusCode());

        assertEquals(699.99, updatedItem.getItemPrice(), 0.0);
        ;

    }

    @Test
    public void updateItem_NotFound() {

        //given
        Item itemToUpdate = new Item(301, "SKU100", "Samsung S10", 699.99);
        String expectedBody = "Item Not Found";

        HttpEntity httpEntity = new HttpEntity(itemToUpdate);
        //when
        ResponseEntity<String> itemResponseEntity = testRestTemplate.exchange(ITEM_URL, HttpMethod.PUT, httpEntity, String.class);

        //then
        assertEquals(HttpStatus.NOT_FOUND, itemResponseEntity.getStatusCode());
        assertEquals(expectedBody, itemResponseEntity.getBody());

    }

    @Test
    public void deleteItem() {

        //given
        Integer itemId = 202;
        URI uri = UriComponentsBuilder.fromUriString(ItemConstants.SINGLE_ITEM).build(itemId);

        //when
        ResponseEntity<Void> itemResponseEntity = testRestTemplate.exchange(uri, HttpMethod.DELETE, null, Void.class);

        //then
        assertEquals(HttpStatus.OK, itemResponseEntity.getStatusCode());
        Item deletedItem = itemService.retrieveItem(itemId);
        assertEquals("000", deletedItem.getItemDescription()); // asserting to find out default item is returned.
    }

    @Test
    public void create_Item_badRequest() {


        //given
        Item item = new Item(100, null, "Iphone XR", 999.99);
        String responseBody = "Please pass valid values for the following fields :[sku]";

        //when
        ResponseEntity<String> itemResponseEntity = testRestTemplate.postForEntity(ITEM_URL, item, String.class);

        //then
        assertEquals(HttpStatus.BAD_REQUEST, itemResponseEntity.getStatusCode());
        assertEquals(responseBody, itemResponseEntity.getBody());


    }


}
