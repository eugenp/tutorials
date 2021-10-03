
package com.shahed.hexagonal.ports;

import core.usecases.ItemServicesImplementation;
import domain.Item;
import java.util.Arrays;
import java.util.List;
import static org.assertj.core.api.Assertions.assertThat;
import org.junit.Before;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.mockito.Mockito;
import org.mockito.internal.verification.VerificationModeFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.TestConfiguration;
import org.springframework.boot.test.mock.mockito.MockBean;
import org.springframework.context.annotation.Bean;
import org.springframework.test.context.junit4.SpringRunner;
import ports.ItemRepository;
import ports.ItemService;

@RunWith(SpringRunner.class)
public class TestItemService {
    @TestConfiguration
    static class ProductServiceTestConfig {
        @Bean
        public ItemService itemService() {
            return new ItemServicesImplementation();
        }
    }

    @MockBean
    private ItemRepository itemRepository;

    @Autowired
    private ItemService itemService;

    @Before
    public void setUp() {
        Item mobilePhone = new Item(1, "Mobile Phone", "Samsung Galaxy S9");
        Item book = new Item(2, "Book", "Kite Runner");
        Item laptop = new Item(3, "Laptop", "Macbook Pro");
        Item electronics = new Item(4, "Electronics", "Bluetooth Speaker");

        List<Item> items = Arrays.asList(mobilePhone, book, laptop);

        Mockito.when(itemRepository.getItems()).thenReturn(items);
        Mockito.when(itemRepository.getItem(mobilePhone.getId())).thenReturn(mobilePhone);
        Mockito.when(itemRepository.getItem(5)).thenReturn(null);
        Mockito.when(itemRepository.addNewItem(electronics)).thenReturn(electronics);
        Mockito.when(itemRepository.removeItem(laptop.getId())).thenReturn(laptop);
    }

    @Test
    public void whenValidProductId_thenProductShouldBeFound() {
        Integer id = 1;
        Item item = itemService.getItem(1);

        assertThat(item.getId()).isEqualTo(id);
        verifyGetByProductIdIsCalledOnce();
    }

    @Test
    public void whenInValidProductId_thenProductShouldNotBeFound() {
        Item item = itemService.getItem(5);

        assertThat(item).isNull();
        verifyGetByProductIdIsCalledOnce();
    }

    @Test
    public void givenThreeProducts_whenGetAllProducts_thenThreeProductsReturned() {
        Item mobilePhone = new Item(1, "Mobile Phone", "Samsung Galaxy S9");
        Item book = new Item(2, "Book", "Kite Runner");
        Item laptop = new Item(3, "Laptop", "Macbook Pro");

        List<Item> allItems = itemService.getItems();

        assertThat(allItems).hasSize(3).extracting(Item::getKind).contains(mobilePhone.getKind(), book.getKind(), laptop.getKind());
        verifyGetProductsIsCalledOnce();
    }

    @Test
    public void whenAddProduct_thenProductTypeIsMatched() {
        Item electronics = new Item(4, "Electronics", "Bluetooth Speaker");

        assertThat(itemService.addNewItem(electronics)).extracting(Item::getKind).as(electronics.getKind());
    }

    @Test
    public void whenRemoveProductById_thenTwoProductReturned() {
        Item laptop = new Item(3, "Laptop", "Macbook Pro");

        assertThat(itemService.removeItem(3)).extracting(Item::getKind).as(laptop.getKind());
    }

    private void verifyGetByProductIdIsCalledOnce() {
        Mockito.verify(itemRepository, VerificationModeFactory.times(1)).getItem(Mockito.anyInt());
        Mockito.reset(itemRepository);
    }

    private void verifyGetProductsIsCalledOnce() {
        Mockito.verify(itemRepository, VerificationModeFactory.times(1)).getItems();
        Mockito.reset(itemRepository);
    }
}
