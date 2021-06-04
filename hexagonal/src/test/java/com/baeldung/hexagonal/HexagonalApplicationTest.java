package com.baeldung.hexagonal;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.mockito.Mockito.verify;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.get;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.jsonPath;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.status;

import java.util.ArrayList;
import java.util.List;

import org.junit.jupiter.api.BeforeAll;
import org.junit.jupiter.api.Test;
import org.mockito.Mockito;
import org.mockito.internal.verification.VerificationModeFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.web.servlet.WebMvcTest;
import org.springframework.boot.test.mock.mockito.MockBean;
import org.springframework.http.MediaType;
import org.springframework.test.web.servlet.MockMvc;
import org.springframework.test.web.servlet.MvcResult;

import com.baeldung.controller.BurgerRestController;
import com.baeldung.model.Burger;
import com.baeldung.service.BurgerService;

@WebMvcTest(BurgerRestController.class)
public class HexagonalApplicationTest {

	@Autowired
	private MockMvc mockMvc;

	@MockBean
	private BurgerService burgerService;

	private static Burger burger;
	private static List<Burger> list;

	@BeforeAll
	public static void setUp() {
		burger = new Burger();
		burger.setName("Dominoes");
		list = new ArrayList<Burger>();
		list.add(burger);
	}

	@Test
	public void givenBurger_whenMockMVC_thenReturnJSONArray() throws Exception {

		Mockito.when(burgerService.listBurger()).thenReturn(list);

		MvcResult mvcResult = mockMvc.perform(get("/burger").contentType(MediaType.APPLICATION_JSON))
				.andExpect(status().isOk()).andExpect(jsonPath("$[0].name").value("Dominoes")).andReturn();

		verify(burgerService, VerificationModeFactory.times(1)).listBurger();
		assertEquals("application/json", mvcResult.getResponse().getContentType());
	}

}
