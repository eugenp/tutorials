package com.baeldung.shopify.adapters.web;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertTrue;
import static org.mockito.Mockito.when;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.get;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.post;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.status;

import java.io.IOException;
import java.util.ArrayList;
import java.util.List;

import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.json.JsonParseException;
import org.springframework.boot.test.autoconfigure.web.servlet.AutoConfigureMockMvc;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.boot.test.mock.mockito.MockBean;
import org.springframework.http.MediaType;
import org.springframework.test.web.servlet.MockMvc;
import org.springframework.test.web.servlet.MvcResult;
import org.springframework.test.web.servlet.request.MockMvcRequestBuilders;

import com.baeldung.shopify.application.domain.Item;
import com.baeldung.shopify.application.services.ShoppingCartService;
import com.fasterxml.jackson.databind.JsonMappingException;
import com.fasterxml.jackson.databind.ObjectMapper;

@SpringBootTest
@AutoConfigureMockMvc
public class ShoppingCartControllerIntegrationTest {

	@Autowired
	private MockMvc mockMvc;

	@MockBean
	private ShoppingCartService shopifyService;

	private static final String SHOPIFY_API = "/shopify";

	@Test
	public void whenPostItems_thenCreateItem_returnsOk() throws Exception {
		Item item = new Item();
		item.setProductId("101");
		item.setName("Apple");
		item.setPrice(210);
		this.mockMvc
				.perform(post(SHOPIFY_API).content(asJsonString(item)).contentType(MediaType.APPLICATION_JSON_VALUE))
				.andExpect(status().isOk());
	}

	@Test
	public void givenItems_whenGetItems_thenReturnOk() throws Exception {
		this.mockMvc.perform(get(SHOPIFY_API)).andExpect(status().isOk());
	}

	@Test
	public void givenItems_whenGetItems_thenReturnJsonArray() throws Exception {
		List<Item> items = new ArrayList<>();

		Item item = new Item();
		item.setProductId("101");
		item.setName("Apple");
		item.setPrice(210);
		items.add(item);

		item = new Item();
		item.setProductId("102");
		item.setName("Rice");
		item.setPrice(65);
		items.add(item);

		when(shopifyService.getItems()).thenReturn(items);
		MvcResult mvcResult = mockMvc
				.perform(MockMvcRequestBuilders.get(SHOPIFY_API).accept(MediaType.APPLICATION_JSON_VALUE)).andReturn();

		int status = mvcResult.getResponse().getStatus();
		assertEquals(200, status);

		String content = mvcResult.getResponse().getContentAsString();
		Item[] itemList = mapFromJson(content, Item[].class);
		assertTrue(itemList.length > 0);
	}

	private String asJsonString(final Object obj) {
		try {
			return new ObjectMapper().writeValueAsString(obj);
		} catch (Exception e) {
			throw new RuntimeException(e);
		}
	}

	protected <T> T mapFromJson(String json, Class<T> clazz)
			throws JsonParseException, JsonMappingException, IOException {

		ObjectMapper objectMapper = new ObjectMapper();
		return objectMapper.readValue(json, clazz);
	}

}
