
package com.shahed.hexagonal.adapters.primary;

import com.fasterxml.jackson.databind.ObjectMapper;
import domain.Item;
import java.util.List;
import java.util.Arrays;
import static net.bytebuddy.matcher.ElementMatchers.is;
import static org.hamcrest.Matchers.hasSize;
import org.junit.Test;
import static org.mockito.BDDMockito.given;
import org.mockito.Mockito;
import static org.mockito.Mockito.reset;
import static org.mockito.Mockito.*;
import org.mockito.internal.verification.VerificationModeFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.mock.mockito.MockBean;
import org.springframework.http.*;
import org.springframework.test.web.servlet.MockMvc;
import org.springframework.test.web.servlet.ResultMatcher;
/*import static org.springframework.test.web.servlet.MockMvcExtensionsKt.post;*/
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.jsonPath;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.status;
import ports.ItemService;
///////////////////////////////////////////////////////////////////////










import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.*;



public class TestItemController {

    @Autowired
    private MockMvc mvc;

    @Autowired
    private ObjectMapper mapper;

    @MockBean
    private ItemService itemService;

    @Test
    public void givenProducts_whenGetProducts_thenReturnJsonArray() throws Exception {
        Item mobilePhone = new Item(1, "Mobile Phone", "Samsung Galaxy S9");
        Item book = new Item(2, "Book", "Kite Runner");
        Item laptop = new Item(3, "Laptop", "Macbook Pro");

        List <Item> items;
        items = Arrays.asList(mobilePhone, book, laptop);

        given(itemService.getItems()).willReturn(items);

        mvc.perform(get("/api/v1/item")
                .contentType(MediaType.APPLICATION_JSON))
                .andExpect(status().isOk())
                .andExpect(jsonPath("$", hasSize(3)))
                .andExpect((ResultMatcher) jsonPath("$[0].kind", is(mobilePhone.getKind())))
                .andExpect((ResultMatcher) jsonPath("$[1].description", is(book.getDescription())));

        verify(itemService, VerificationModeFactory.times(1)).getItems();
        reset(itemService);
    }

    @Test
    public void givenProduct_whenGetProductById_thenReturnValidProduct() throws Exception {
        Item mobilePhone = new Item(1, "Mobile Phone", "Samsung Galaxy S9");
        given(itemService.getItem(Mockito.anyInt())).willReturn(mobilePhone);

        mvc.perform(get("/api/v1/item/1")
                .contentType(MediaType.APPLICATION_JSON))
                .andExpect(status().isOk())
                .andExpect((ResultMatcher) jsonPath("$.productId", is(mobilePhone.getId())));

        verify(itemService, VerificationModeFactory.times(1)).getItem(Mockito.anyInt());
        reset(itemService);
    }

    @Test
    public void whenPostProduct_thenCreateProduct() throws Exception {
        Item book = new Item(1, "Book", "Kite Runner");
        given(itemService.addNewItem((Item) Mockito.any())).willReturn(book);

        mvc.perform(post("/api/v1/item")
                .contentType(MediaType.APPLICATION_JSON)
                .content(mapper.writeValueAsString(book))
                .accept(MediaType.APPLICATION_JSON))
                .andExpect(status().isCreated())
                .andExpect((ResultMatcher) jsonPath("$.kind", is(book.getKind())));

        verify(itemService, VerificationModeFactory.times(1)).addNewItem((Item) Mockito.any());
        reset(itemService);
    }

    @Test
    public void whenDeleteProduct_thenRemoveValidProduct() throws Exception {
        Item book = new Item(1, "Book", "Kite Runner");
        given(itemService.removeItem(Mockito.anyInt())).willReturn(book);

        mvc.perform(delete("/api/v1/item/1")
                .contentType(MediaType.APPLICATION_JSON))
                .andExpect(status().isOk())
                .andExpect((ResultMatcher) jsonPath("$.itemId", is(book.getId())));

        verify(itemService, VerificationModeFactory.times(1)).removeItem(Mockito.anyInt());
        reset(itemService);
    }
}