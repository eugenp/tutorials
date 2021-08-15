package com.baeldung.hexagon;

import com.baeldung.hexagon.core.domain.Burger;
import com.baeldung.hexagon.port.inbound.BurgerService;
import org.junit.jupiter.api.Assertions;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;

import java.util.List;

import static org.junit.jupiter.api.Assertions.assertNotNull;

@SpringBootTest
class HexagonApplicationTests {

	@Autowired
	private BurgerService burgerService;

	@Test
	void testCreate() {
		Burger burger = new Burger();
		burger.setName("Chicken Burger");
		burger.setAddons(new String[]{"tomato", "cheese"});
		burgerService.createBurger(burger);
		assertNotNull(burgerService.findByName("Chicken Burger"));
	}

	@Test
	void testFindByName() {
		Burger burger = new Burger();
		burger.setName("Chicken Burger");
		burger.setAddons(new String[]{"tomato", "cheese"});
		burgerService.createBurger(burger);
		Burger burger1 = burgerService.findByName("Chicken Burger");
		Assertions.assertEquals("Chicken Burger", burger1.getName());
	}

	@Test
	void testFindAll() {
		Burger burger1 = new Burger();
		burger1.setName("Chicken Burger");
		burger1.setAddons(new String[]{"tomato", "cheese"});
		burgerService.createBurger(burger1);

		Burger burger2 = new Burger();
		burger2.setName("Veggie Burger");
		burger2.setAddons(new String[]{"onion", "cheese"});
		burgerService.createBurger(burger2);
		List<Burger> burgers = burgerService.findAll();

		Assertions.assertEquals(2, burgers.size());
	}

}
